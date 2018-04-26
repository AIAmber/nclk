package cn.com.sparknet.nclk.reportserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 上报服务器，用于接收子钟上报的数据
 *
 * @author tangzj
 */
public class ReportServerMain {
    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ReportServerInitializer())
                    .childOption(ChannelOption.TCP_NODELAY, true);
            ChannelFuture f = b.bind(port).sync();
            System.out.println("服务器已启动");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        ReportServerMain server = new ReportServerMain();
        server.start(7788);
        //server.start(8899);
    }
}
