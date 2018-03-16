package cn.com.sparknet.nclk.reportserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * netty通道初始化
 *
 * @author tangzj
 */
public class ReportServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new PackageDecoder());
        ch.pipeline().addLast(new ReportServerInHandler());
        ch.pipeline().addLast(new ExceptionInHandler());
    }
}
