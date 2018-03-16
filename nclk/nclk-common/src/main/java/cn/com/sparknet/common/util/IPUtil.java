package cn.com.sparknet.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取服务器IP及Mac
 * @author chenxy
 *
 */
public final class IPUtil {

	private IPUtil() {
	}

	/**
	 * 获取服务器IP地址
	 * @return
	 */
	public static String getLocalIP() {
		String ip = "";
		InetAddress inetAddress = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					inetAddress = (InetAddress) ips.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if (null != inetAddress) {
			ip = inetAddress.getHostAddress();
		}
		return ip;
	}

	/**
	 * 获取服务器IP地址(多网卡)
	 * @return
	 */
	public static List<String> getLocalIPs() {
		InetAddress inetAddress = null;
		List<String> ipList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					inetAddress = (InetAddress) ips.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						ipList.add(inetAddress.getHostAddress());
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return ipList;
	}

	/**
	 * 获取服务器Mac地址
	 */
	public static String getMacId() {
		String macId = "";
		InetAddress inetAddress = null;
		NetworkInterface ni = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					inetAddress = (InetAddress) ips.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if (null != inetAddress) {
			try {
				macId = getMacFromBytes(ni.getHardwareAddress());
			} catch (SocketException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return macId;
	}

	/**
	 * 获取服务器Mac地址(多网卡)
	 */
	public static List<String> getMacIds() {
		InetAddress inetAddress = null;
		NetworkInterface ni = null;
		List<String> macList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					inetAddress = (InetAddress) ips.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
						macList.add(getMacFromBytes(ni.getHardwareAddress()));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return macList;
	}

	private static String getMacFromBytes(byte[] bytes) {
		StringBuffer mac = new StringBuffer();
		byte currentByte;
		boolean first = false;
		for (byte b : bytes) {
			if (first) {
				mac.append("-");
			}
			currentByte = (byte) ((b & 240) >> 4);
			mac.append(Integer.toHexString(currentByte));
			currentByte = (byte) (b & 15);
			mac.append(Integer.toHexString(currentByte));
			first = true;
		}
		return mac.toString().toUpperCase();
	}
}
