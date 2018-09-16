package org.xsl781.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @author xsl781
 * 
 */
public final class MD5Util {
	/**
	 * 加密方法:md5属于单向加密，不可逆的，比如：用加密后的字符串再进行加密，结果不会和加密前的一样
	 * 
	 * @param s
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static final String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] btInput = s.getBytes();
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("没有合适的加密方法");
			System.out.println(e.getMessage());
			return null;
		}
	}
}
