package cn.com.sparknet.nclk.endpointserver.core;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;

import cn.com.sparknet.common.config.Config;
import cn.com.sparknet.common.util.SpringContextUtil;
import cn.com.sparknet.nclk.endpointserver.bean.TimeInfo;
import cn.com.sparknet.nclk.endpointserver.task.CycleCheckTask;
import cn.com.sparknet.nclk.service.EndPointService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
    private static final String TIME_NOT_CONSISTENT_MSG = "网络时钟与母钟时间偏差过大，比母钟%s，约%s秒";

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
            long deviationSeconds = getSeconds(timeInfo) - getSeconds(endPointService.getParentClock());
            boolean negative;
            deviationSeconds = (negative = deviationSeconds < 0) ? -deviationSeconds : deviationSeconds;
            if (deviationSeconds > MAX_DEVIATION_SECONDS) {
                CycleCheckTask.removeHealth(ip);
                String errorMsg = String.format(TIME_NOT_CONSISTENT_MSG, negative ? "慢" : "快", deviationSeconds);
                endPointService.sendErrorInfo(ip, TIME_NOT_CONSISTENT, errorMsg);
            } else {
                CycleCheckTask.addHealth(ip);
            }
        } finally {
            byteBuf.release();
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
}
