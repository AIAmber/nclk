package cn.com.sparknet.nclk.endpointserver.core;

import cn.com.sparknet.common.config.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author tangzj
 */
public class EndPointServer {

    private EventLoopGroup bossGroup = new NioEventLoopGroup(2);
    private EventLoopGroup workerGroup = new NioEventLoopGroup(4);
    private ChannelFuture channelFuture;

    public ChannelFuture startListen() throws InterruptedException {
        String portStr = Config.getInstance().getProperty("reportserver.port");
        int port = Integer.parseInt(portStr);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new EndPointServerInitializer())
                .childOption(ChannelOption.TCP_NODELAY, true);
        return channelFuture = b.bind(port).sync();
    }

    public void destroy() throws InterruptedException {
        channelFuture.channel().close().sync();
        workerGroup.shutdownGracefully().sync();
        bossGroup.shutdownGracefully().sync();
    }
}
