package cn.com.sparknet.nclk.endpointserver.core;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import cn.com.sparknet.common.config.Config;
import cn.com.sparknet.common.util.SpringContextUtil;
import cn.com.sparknet.nclk.endpointserver.bean.TimeInfo;
import cn.com.sparknet.nclk.endpointserver.task.CycleCheckTask;
import cn.com.sparknet.nclk.endpointserver.util.NettyUtil;
import cn.com.sparknet.nclk.service.EndPointService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import static cn.com.sparknet.nclk.endpointserver.core.ClockErrorType.TIME_NOT_CONSISTENT;

/**
 * 业务处理handler，用于接收子钟上报的时间
 *
 * @author tangzj
 */
public class EndPointServerInHandler extends ChannelInboundHandlerAdapter {
    /**
     * 允许最大偏差时间
     */
    private static final double MAX_DEVIATION_SECONDS = Double.parseDouble(Config.getInstance().getProperty("reportserver.maxDeviationSeconds"));
    /**
     * 扫描设备固定命令
     */
    private static final byte[] SCAN_DEVICES = {(byte) 0xff, 0x01, 0x01, 0x02};
    /**
     * 扫描设备固定端口
     */
    private static final int SCAN_PORT = 1901;
    private static final String TIME_NOT_CONSISTENT_MSG = "时间偏差（子钟与母钟时间存在误差，母钟时间:%s 子钟时间：%s）";
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

    private Logger logger = Logger.getLogger(getClass());

    private EndPointService endPointService = SpringContextUtil.getBean(EndPointService.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        try {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);

            String hexString = Hex.encodeHexString(bytes);
            int hour = Integer.parseInt(hexString.substring(0, 2), 16);
            int minute = Integer.parseInt(hexString.substring(2, 4), 16);
            int second = Integer.parseInt(hexString.substring(4, 6), 16);

            String ip = getIpAddress(ctx);
            TimeInfo timeInfo = new TimeInfo();
            timeInfo.setHour(hour);
            timeInfo.setMinute(minute);
            timeInfo.setSecond(second);
            CycleCheckTask.updateReportTime(ip);
            TimeInfo parentTimeInfo = endPointService.getParentClock();
            long deviationSeconds = getSeconds(timeInfo) - getSeconds(parentTimeInfo);
            if (Math.abs(deviationSeconds) > MAX_DEVIATION_SECONDS) {
                CycleCheckTask.removeHealth(ip);
                final String errorMsg = String.format(TIME_NOT_CONSISTENT_MSG, timeInfoToString(parentTimeInfo), timeInfoToString(timeInfo));
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        endPointService.sendErrorInfo(ip, TIME_NOT_CONSISTENT, errorMsg);
                    }
                });
            } else {
                CycleCheckTask.addHealth(ip);
            }
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent ev = (IdleStateEvent) evt;
            if (ev.state() == IdleState.READER_IDLE) {
                DatagramSocket datagramSocket = null;
                try {
                    datagramSocket = new DatagramSocket();
                    datagramSocket.setSoTimeout(1000);
                    datagramSocket.send(new DatagramPacket(SCAN_DEVICES, SCAN_DEVICES.length, InetAddress.getByName(getIpAddress(ctx)), SCAN_PORT));
                    byte[] receBuf = new byte[1024];
                    DatagramPacket datagramPacket = new DatagramPacket(receBuf, receBuf.length);
                    datagramSocket.receive(datagramPacket);
                    //如果成功接收到数据，则一切正常，抛出超时，表明设备可能不在线，断开网络连接
                } catch (SocketTimeoutException e) {
                    //忽略异常：扫描超时
                    NettyUtil.close(ctx.channel());
                } finally {
                    IOUtils.closeQuietly(datagramSocket);
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CycleCheckTask.addClock(getIpAddress(ctx));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        CycleCheckTask.removeClock(getIpAddress(ctx));
    }

    private String getIpAddress(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        return inetSocketAddress.getHostString();
    }

    /**
     * 以一天的0点位起始点，得到当前时间与0点相差的秒数
     *
     * @return 获得是秒
     */
    private long getSeconds(TimeInfo timeInfo) {
        return timeInfo.getHour() * 3600L + timeInfo.getMinute() * 60L + timeInfo.getSecond();
    }

    /**
     * 将timeinfo格式化成HH:mm:ss形式
     *
     * @param timeInfo
     * @return
     */
    private String timeInfoToString(TimeInfo timeInfo) {
        String hour = String.valueOf(timeInfo.getHour());
        String minute = String.valueOf(timeInfo.getMinute());
        String second = String.valueOf(timeInfo.getSecond());
        hour = hour.length() == 1 ? ("0" + hour) : hour;
        minute = minute.length() == 1 ? ("0" + minute) : minute;
        second = second.length() == 1 ? ("0" + second) : second;
        return hour + ":" + minute + ":" + second;
    }
}
