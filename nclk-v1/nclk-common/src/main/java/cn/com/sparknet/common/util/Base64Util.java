package cn.com.sparknet.common.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64编码与解码
 * @author chenxy
 *
 */
public final class Base64Util {
	
	private Base64Util(){
	}
	
	/**
	 * 加密
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(byte[] bytes) throws Exception {
		return Base64.encodeBase64String(bytes);
	}
	
	/**
	 * 加密
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String encode(String content) throws Exception {
		return encode(content.getBytes());
	}
	
	/**
	 * 加密
	 * @param content
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String encode(String content,String charset) throws Exception {
		return encode(content.getBytes(charset));
	}

	/**
	 * 解密
	 * @param content
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String decode(String content,String charset) throws Exception {
		return new String(Base64.decodeBase64(content), charset);
	}
	
	/**
	 * 解密
	 * @param bytes
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String decode(byte[] bytes,String charset) throws Exception {
		return new String(Base64.decodeBase64(bytes), charset);
	}
	
}
