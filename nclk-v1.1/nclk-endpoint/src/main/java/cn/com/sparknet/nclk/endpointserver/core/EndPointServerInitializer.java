package cn.com.sparknet.nclk.endpointserver.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * netty通道初始化
 *
 * @author tangzj
 */
public class EndPointServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new FixedLengthFrameDecoder(3));
        ch.pipeline().addLast(new EndPointServerInHandler());
    }
}
