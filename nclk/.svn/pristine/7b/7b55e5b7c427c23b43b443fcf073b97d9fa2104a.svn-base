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
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.sparknet.common.config.Config;
import cn.com.sparknet.common.json.ListJson;
import cn.com.sparknet.common.util.HttpClientUtils;
import cn.com.sparknet.common.util.JsonUtil;
import cn.com.sparknet.nclk.bean.DeviceInfo;
import cn.com.sparknet.nclk.endpointserver.bean.TimeInfo;
import cn.com.sparknet.nclk.endpointserver.task.ClockErrorType;
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
    private static final int SCAN_TIMEOUT = Integer.parseInt(Config.getInstance().getProperty("reportserver.scanTimeOut"));
    /**
     * 当前考点编号
     */
    private static final String ENDPOINT_NO = Config.getInstance().getProperty("endPointNo");
    /**
     * 将子钟状态更新为可用URL
     */
    private static final String ENABLE_ERROR_CLOCK_URL = Config.getInstance().getProperty("url.enableErrorClock");
    /**
     * 获取有异常的子钟URL
     */
    private static final String FIND_ERROR_CLOCK_URL = Config.getInstance().getProperty("url.findErrorClock");
    /**
     * 上报异常URL
     */
    private static final String SAVE_ERRORINFO_URL = Config.getInstance().getProperty("url.saveErrorInfo");
    /**
     * 获得需要监控的子钟URL
     */
    private static final String FIND_ALL_MONITOREDCLOCK_URL = Config.getInstance().getProperty("url.findAllMonitoredClock");

    private static TimeInfo parentClock;

    private Logger logger = Logger.getLogger(getClass().getName());
    private List<String> monitoredClocks;

    @Override
    public TimeInfo getParentClock() {
        return parentClock;
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

        System.err.println(ip + " : " + errorMsg + " : 错误类型[" + errorType + "]");
        Map<String, Object> params = new HashMap<>(4);
        params.put("endPointNo", ENDPOINT_NO);
        params.put("ip", ip);
        params.put("errorType", errorType.toMsgString());
        params.put("errorMsg", errorMsg);
        try {
            HttpClientUtils.post(SAVE_ERRORINFO_URL, params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 更新母钟时间
     */
    @Override
    public void updateParentClockTime() {
        //TODO 母钟时间获取方式未知，这里先这么着
        Calendar calendar = Calendar.getInstance();

        //主要为多线程的线程不安全考虑，使用一个副本对象赋值
        TimeInfo timeInfo = new TimeInfo();
        timeInfo.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timeInfo.setMinute(calendar.get(Calendar.MINUTE));
        timeInfo.setSecond(calendar.get(Calendar.SECOND));
        parentClock = timeInfo;
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
        List<DeviceInfo> list = new ArrayList<>();
        listJson.setRows(list);

        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
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
            IOUtils.closeQuietly(datagramSocket);
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
            HttpClientUtils.post(ENABLE_ERROR_CLOCK_URL, params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static int hexByteToInt(byte bs) {
        return Integer.parseInt(Hex.encodeHexString(new byte[]{bs}), 16);
    }

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
        //解析设备名称
        /*int l = 0;
        for (int i = 19; i < 35; i++) {
            if (res[i] == 0x00) {
                break;
            }
            l++;
        }
        byte[] nameBs = new byte[l];
        System.arraycopy(res, 19, nameBs, 0, nameBs.length);
        System.out.println("设备名称：" + new String(nameBs, "GBK"));*/
    }

    /**
     * 从listjson返回结果中提取出list字符串
     *
     * @param json
     * @return
     */
    private static List<String> parseJsonToList(String json) {
        Map<?, ?> map = JsonUtil.jsonToMap(json);
        Object obj = map.get("rows");
        if (obj == null) {
            return null;
        }
        JSONArray array = (JSONArray) obj;
        List<String> list = new ArrayList<>(array.size());
        for (int i = 0, l = array.size(); i < l; i++) {
            list.add(array.get(i).toString());
        }
        return list;
    }
}
