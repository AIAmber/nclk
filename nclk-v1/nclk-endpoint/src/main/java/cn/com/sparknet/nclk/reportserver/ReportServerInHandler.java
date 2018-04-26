package cn.com.sparknet.nclk.reportserver;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 业务处理handler，用于接收子钟上报的时间
 *
 * @author tangzj
 */
public class ReportServerInHandler extends ChannelInboundHandlerAdapter {

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
            System.out.println(hour + ":" + minute + ":" + second);
            //System.out.println("remote：" + ctx.channel().remoteAddress());
        } finally {
            byteBuf.release();
        }
    }

}
