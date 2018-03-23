package cn.com.sparknet.nclk.endpointserver.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * netty通道初始化
 *
 * @author tangzj
 */
public class EndPointServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new PackageDecoder());
        ch.pipeline().addLast(new EndPointServerInHandler());
    }
}
