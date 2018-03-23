package cn.com.sparknet.nclk.endpointserver.core;

import java.util.List;

import cn.com.sparknet.common.config.Config;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 1、分包、拆包处理
 * 2、重复包处理（由于子钟的bug，有时候会连续上传几十个重复的数据，这里把这些数据抛弃掉，只取一条）
 *
 * @author tangzj
 */
public class PackageDecoder extends ByteToMessageDecoder {

    private static final int PACKAGE_LENGTH = 3;

    /**
     * 上次上报数据的时间点
     */
    private long lastTime = 0L;
    /**
     * 两次上报时间间隔
     */
    private static final long INTERVAL_TIME = Long.parseLong(Config.getInstance().getProperty("reportserver.report.intervalTime"));

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf byteBuf = decodePackage(in);
        if (byteBuf != null) {
            long interval = System.currentTimeMillis() - lastTime;
            if (interval > INTERVAL_TIME) {
                lastTime = System.currentTimeMillis();
                out.add(byteBuf);
            } else {
                byteBuf.release();
            }
        }
    }

    private ByteBuf decodePackage(ByteBuf in) throws Exception {
        if (in.readableBytes() < PACKAGE_LENGTH) {
            //由于子钟存在有时候不上传秒的bug，会导致长传数据的长度小于3，所以这里将数据量小于3的数据全部抛弃
            //由于每次数据量都不会很大，所以不会出现分包的情况
            in.resetReaderIndex();
            in.resetWriterIndex();
            return null;
        } else {
            ByteBuf byteBuf = in.readRetainedSlice(PACKAGE_LENGTH);
            byteBuf.markReaderIndex();
            byte[] bs = new byte[PACKAGE_LENGTH];
            byteBuf.readBytes(bs);
            if (bs[2] == (byte) 0xff) {
                //子钟上报数据存在一种错误情况，秒会变成0xff，这里将有问题的数据排除掉
                byteBuf.release();
                return null;
            }
            byteBuf.resetReaderIndex();
            return byteBuf;
        }
    }
}
