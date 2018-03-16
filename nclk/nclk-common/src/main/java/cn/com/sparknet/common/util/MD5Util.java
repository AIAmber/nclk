package cn.com.sparknet.common.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5-消息摘要算法
 * @author chenxy
 *
 */
public final class MD5Util {
	
	private MD5Util(){
	}
	
	/**
	 * 生成MD5值
	 * @param data
	 * @return
	 */
	public static String encrypt(String data) {
		return DigestUtils.md5Hex(data);
	}
	
	/**
	 * 生成MD5值
	 * @param data
	 * @return
	 */
	public static String encrypt(byte[] data) {
		return DigestUtils.md5Hex(data);
	}
	
	/**
	 * 生成MD5值
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String encrypt(InputStream data) throws IOException {
		return DigestUtils.md5Hex(data);
	}
	
}
