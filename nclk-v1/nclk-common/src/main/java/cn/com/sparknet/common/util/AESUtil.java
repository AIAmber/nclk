package cn.com.sparknet.common.util;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES-对称加密算法
 * 相比DES具有更高的计算效率及安全性
 * @author chenxy
 * 
 */
public final class AESUtil {
	
	private AESUtil(){
	}

	/**
	 * 加密
	 * @param content 加密内容
	 * @param password 加密密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		//防止linux下随机生成key
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );   
        secureRandom.setSeed(password.getBytes());
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(content);
	}
	
	/**
	 * 解密
	 * @param content 解密内容
	 * @param password 解密密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		//防止linux下随机生成key
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );   
        secureRandom.setSeed(password.getBytes());
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(content);
	}
	
}
