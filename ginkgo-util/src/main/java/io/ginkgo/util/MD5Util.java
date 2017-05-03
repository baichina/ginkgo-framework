package io.ginkgo.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 * MD5工具类
 * 
 * @since 1.0.0
 * @author Barry
 */
@SuppressWarnings("restriction")
public class MD5Util {

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            原字符串
	 * @return
	 */
	public static String encode(String str) {
		String newstr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			newstr = base64en.encode(md5.digest(str.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}

	/**
	 * MD5加密密码
	 * <p>
	 * 进行多重加密，防止暴力破解
	 * </p>
	 * 
	 * @param password
	 *            原密码
	 * @return
	 */
	public static String encodePassword(String password) {
		return encode("*" + encode(password));
	}
}