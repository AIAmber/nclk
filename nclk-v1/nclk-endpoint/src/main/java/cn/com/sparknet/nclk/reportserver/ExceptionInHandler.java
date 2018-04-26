package cn.com.sparknet.nclk.reportserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 异常集中处理handler
 *
 * @author tangzj
 */
public class ExceptionInHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //这里暂时先打印一下异常信息
        cause.printStackTrace();
    }
}
