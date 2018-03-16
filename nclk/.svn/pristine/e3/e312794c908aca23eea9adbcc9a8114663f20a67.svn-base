package cn.com.sparknet.nclk.udpclient;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * @author tangzj
 */
public class UdpClientsMain {

    private static final byte[] SCAN_DEVICES = {(byte) 0xff, 0x01, 0x01, 0x02};
    private static final String ADDRESS = "255.255.255.255";
    private static final int PORT = 1901;


    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket;
        datagramSocket = new DatagramSocket();
        datagramSocket.setBroadcast(true);
        datagramSocket.setSoTimeout(1000);

        //发送数据
        InetAddress inetAddress = InetAddress.getByName(ADDRESS);
        DatagramPacket datagramPacket = new DatagramPacket(SCAN_DEVICES, SCAN_DEVICES.length, inetAddress, PORT);
        datagramSocket.send(datagramPacket);


        try {
            receiveDate(datagramSocket);
        } catch (SocketTimeoutException e) {
            System.err.println("扫描超时");
        } finally {
            datagramSocket.close();
        }
    }

    private static int hexByteToInt(byte bs) {
        return Integer.parseInt(Hex.encodeHexString(new byte[]{bs}), 16);
    }

    private static void receiveDate(DatagramSocket datagramSocket) throws IOException {
        byte[] receBuf = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(receBuf, receBuf.length);
        datagramSocket.receive(datagramPacket);
        System.out.println(">>>" + datagramPacket.getAddress().getHostAddress());
        byte[] res = new byte[datagramPacket.getLength()];
        System.arraycopy(receBuf, 0, res, 0, res.length);
        String resStr = Hex.encodeHexString(res);
        System.out.println(resStr);

        //解析IP地址
        String ip = hexByteToInt(receBuf[5]) + "." + hexByteToInt(receBuf[6]) + "." +
                hexByteToInt(receBuf[7]) + "." + hexByteToInt(receBuf[8]);
        System.out.println(ip);

        //解析MAC地址
        char[] chars = resStr.substring(18, 30).toUpperCase().toCharArray();
        StringBuilder builder = new StringBuilder(17);
        for (int i = 0, l = chars.length; i < l; i++) {
            builder.append(chars[i]);
            if (i < l - 1 && i % 2 != 0) {
                builder.append(":");
            }
        }
        String mac = builder.toString();
        System.out.println(mac);

        //解析设备名称
        int l = 0;
        for (int i = 19; i < 35; i++) {
            if (res[i] == 0x00) {
                break;
            }
            l++;
        }
        byte[] nameBs = new byte[l];
        System.arraycopy(res, 19, nameBs, 0, nameBs.length);
        System.out.println(new String(nameBs, "GBK"));
    }

}  