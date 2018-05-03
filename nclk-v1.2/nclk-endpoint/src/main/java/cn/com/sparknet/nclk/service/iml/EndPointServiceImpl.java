package cn.com.sparknet.nclk.service.iml;

import com.alibaba.fastjson.JSONArray;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import cn.com.sparknet.common.config.Config;
import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.common.util.HttpClientUtils;
import cn.com.sparknet.common.util.JsonUtil;
import cn.com.sparknet.nclk.bean.DeviceInfo;
import cn.com.sparknet.nclk.endpointserver.bean.TimeInfo;
import cn.com.sparknet.nclk.endpointserver.core.ClockErrorType;
import cn.com.sparknet.nclk.endpointserver.util.NtpTime;
import cn.com.sparknet.nclk.service.EndPointService;

/**
 * @author tangzj
 */
@Service
public class EndPointServiceImpl implements EndPointService {

    /**
     * 扫描设备固定命令
     */
    private static final byte[] SCAN_DEVICES = {(byte) 0xff, 0x01, 0x01, 0x02};
    /**
     * UDP广播地址
     */
    private static final String BROADCAST_ADDRESS = "255.255.255.255";
    /**
     * 扫描设备固定端口
     */
    private static final int SCAN_PORT = 1901;
    /**
     * 扫描超时时间
     */
    private static final int SCAN_TIMEOUT = Integer.parseInt(Config.getInstance().getProperty("reportserver.scanTimeout"));

    /**
     * 当前考点编号
     */
    private static final String ENDPOINT_NO = Config.getInstance().getProperty("endPointNo");

    /**
     * 中心API接口IP地址
     */
    private static final String CENTER_API_IP = Config.getInstance().getProperty("center.api.ip");
    /**
     * 中心API接口端口号
     */
    private static final int CENTER_API_PORT = Integer.parseInt(Config.getInstance().getProperty("center.api.port"));
    /**
     * 将子钟状态更新为可用URL
     */
    private static final String ENABLE_ERROR_CLOCK_URL = "http://" + CENTER_API_IP + ":" + CENTER_API_PORT + "/nclk-center/EndPointApiController/enableErrorClock";
    /**
     * 获取有异常的子钟URL
     */
    private static final String FIND_ERROR_CLOCK_URL = "http://" + CENTER_API_IP + ":" + CENTER_API_PORT + "/nclk-center/EndPointApiController/findErrorClock";
    /**
     * 上报异常URL
     */
    private static final String SAVE_ERRORINFO_URL = "http://" + CENTER_API_IP + ":" + CENTER_API_PORT + "/nclk-center/EndPointApiController/saveErrorInfo";
    /**
     * 获得需要监控的子钟URL
     */
    private static final String FIND_ALL_MONITOREDCLOCK_URL = "http://" + CENTER_API_IP + ":" + CENTER_API_PORT + "/nclk-center/EndPointApiController/findAllMonitoredClock";

    private static Logger logger = Logger.getLogger(EndPointServiceImpl.class.getName());
    private List<String> monitoredClocks;
    private static ExecutorService executorService = Executors.newFixedThreadPool(50, new ThreadFactory() {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        @Override
        public Thread newThread(Runnable r) {
            Thread t = threadFactory.newThread(r);
            //所有的线程设置为守护线程，程序退出后可以直接退出
            t.setDaemon(true);
            return t;
        }
    });

    @Override
    public TimeInfo getParentClock() {
        Date date = NtpTime.getInstance().getServerTimeWithDeviation();
        date = date == null ? new Date() : date;
        TimeInfo timeInfo = new TimeInfo();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        timeInfo.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timeInfo.setMinute(calendar.get(Calendar.MINUTE));
        timeInfo.setSecond(calendar.get(Calendar.SECOND));
        return timeInfo;
    }

    /**
     * 向中心发送错误信息
     *
     * @param ip        子钟IP
     * @param errorType 异常分类
     * @param errorMsg  错误描述
     */
    @Override
    public void sendErrorInfo(String ip, ClockErrorType errorType, String errorMsg) {
        //如果没有需要监控的子钟，就不要上报错误信息
        if (monitoredClocks == null || monitoredClocks.size() == 0 || !monitoredClocks.contains(ip)) {
            return;
        }

        Map<String, Object> params = new HashMap<>(4);
        params.put("endPointNo", ENDPOINT_NO);
        params.put("ip", ip);
        params.put("errorType", errorType.toMsgString());
        params.put("errorMsg", errorMsg);
        try {
            String json = HttpClientUtils.post(SAVE_ERRORINFO_URL, params);
            parseJson(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 从中心获得所有需要监控的子钟IP
     *
     * @return
     */
    @Override
    public List<String> findAllMonitoredClock() {
        try {
            String json = HttpClientUtils.get(FIND_ALL_MONITOREDCLOCK_URL + "?endPointNo=" + ENDPOINT_NO);
            return monitoredClocks = parseJsonToList(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ListJson scanDevice() {
        ListJson listJson = new ListJson();
        final List<DeviceInfo> list = new ArrayList<>();
        listJson.setRows(list);

        try {
            List<String> ips = getLocalIP();
            if (ips.size() > 0) {
                CountDownLatch countDownLatch = new CountDownLatch(ips.size());
                for (String ip : ips) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            DatagramSocket datagramSocket = null;
                            try {
                                datagramSocket = new DatagramSocket(new InetSocketAddress(ip, 0));
                                datagramSocket.setBroadcast(true);
                                datagramSocket.setSoTimeout(SCAN_TIMEOUT);
                                datagramSocket.send(new DatagramPacket(SCAN_DEVICES, SCAN_DEVICES.length, InetAddress.getByName(BROADCAST_ADDRESS), SCAN_PORT));
                                while (true) {
                                    DeviceInfo deviceInfo = receiveDate(datagramSocket);
                                    list.add(deviceInfo);
                                }
                            } catch (SocketTimeoutException e) {
                                //忽略异常：扫描超时
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            } finally {
                                countDownLatch.countDown();
                                IOUtils.closeQuietly(datagramSocket);
                            }

                        }
                    });
                }
                //最多等待10秒，不再等待，通常情况下并不会等待那么长时间
                countDownLatch.await(10, TimeUnit.SECONDS);
            }
        } catch (InterruptedException | SocketException e) {
            logger.error(e.getMessage(), e);
        }
        listJson.setTotal(list.size());
        return listJson;
    }

    @Override
    public List<String> findErrorClock() {
        try {
            String json = HttpClientUtils.get(FIND_ERROR_CLOCK_URL + "?endPointNo=" + ENDPOINT_NO);
            return parseJsonToList(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void enableErrorClock(String ip) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("endPointNo", ENDPOINT_NO);
        params.put("ip", ip);
        try {
            String json = HttpClientUtils.post(ENABLE_ERROR_CLOCK_URL, params);
            parseJson(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static int hexByteToInt(byte bs) {
        return Integer.parseInt(Hex.encodeHexString(new byte[]{bs}), 16);
    }

    /**
     * 接收广播出去的结果，每次只能接受一个返回，需要循环接受完
     *
     * @param datagramSocket
     * @return
     * @throws IOException
     */
    private static DeviceInfo receiveDate(DatagramSocket datagramSocket) throws IOException {
        byte[] receBuf = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(receBuf, receBuf.length);
        datagramSocket.receive(datagramPacket);
        byte[] res = new byte[datagramPacket.getLength()];
        System.arraycopy(receBuf, 0, res, 0, res.length);
        String resStr = Hex.encodeHexString(res);

        DeviceInfo deviceInfo = new DeviceInfo();

        //解析IP地址
        String ip = hexByteToInt(receBuf[5]) + "." + hexByteToInt(receBuf[6]) + "." +
                hexByteToInt(receBuf[7]) + "." + hexByteToInt(receBuf[8]);
        deviceInfo.setIp(ip);

        //解析MAC地址
        char[] chars = resStr.substring(18, 30).toUpperCase().toCharArray();
        StringBuilder builder = new StringBuilder(17);
        for (int i = 0, l = chars.length; i < l; i++) {
            builder.append(chars[i]);
            if (i < l - 1 && i % 2 != 0) {
                builder.append(":");
            }
        }
        deviceInfo.setMac(builder.toString());
        return deviceInfo;
    }

    private static void parseJson(String json) {
        Map<?, ?> map = JsonUtil.jsonToMap(json);
        if (!Boolean.valueOf(map.get("success").toString())) {
            logger.error(map.get("bean"));
        }
    }

    /**
     * 从listjson返回结果中提取出list字符串
     *
     * @param json
     * @return
     */
    private static List<String> parseJsonToList(String json) {
        Map<?, ?> map = JsonUtil.jsonToMap(json);
        JSONArray array = (JSONArray) map.get("rows");
        List<String> list = new ArrayList<>(array.size());
        for (int i = 0, l = array.size(); i < l; i++) {
            list.add(array.get(i).toString());
        }
        return list;
    }

    /**
     * 获得本机IP地址，不包含127.0.0.1
     *
     * @return
     * @throws SocketException
     */
    private static List<String> getLocalIP() throws SocketException {
        List<String> ipList = new ArrayList<>();
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        String localIp = "127.0.0.1";
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            // 遍历所有ip
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                InetAddress ip = ips.nextElement();
                if (null == ip || "".equals(ip.toString())) {
                    continue;
                }
                String sIP = ip.getHostAddress();
                if (sIP == null || sIP.contains(":") || localIp.equals(sIP)) {
                    continue;
                }
                ipList.add(sIP);
            }
        }
        return ipList;
    }
}
