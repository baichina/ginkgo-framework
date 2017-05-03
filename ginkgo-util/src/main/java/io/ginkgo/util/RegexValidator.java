package io.ginkgo.util;

/**
 * 正则表达式校验器
 * 
 * @since 1.0.0
 * @author Barry
 */
public final class RegexValidator {

	/**
	 * 验证邮箱地址，@前只能包含字母、数字、'_'、'、'、'.'
	 * 
	 * @param email
	 *            邮箱地址
	 * @return
	 */
	public static boolean isEmail(String email) {
		String regex = "^[\\w\\-\\.]+@[a-z0-9A-Z\\-]+(\\.[a-zA-Z]{2,}){1,2}$";
		return RegexUtil.matches(email, regex);
	}

	/**
	 * 验证11位手机号码
	 * 
	 * @param mobileNo
	 *            手机号码
	 * @return
	 */
	public static boolean isMobileNo(String mobileNo) {
		// String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		String regex = "^1\\d{10}$";
		return RegexUtil.matches(mobileNo, regex);
	}

	/**
	 * 验证15位或18位身份证号码
	 * 
	 * @param idCardNo
	 *            身份证号码
	 * @return
	 */
	public static boolean isIdCardNo(String idCardNo) {
		String regex15 = "^\\d{15}$";
		String regex18 = "^\\d{17}([0-9]|X|x)$";
		return RegexUtil.matches(idCardNo, regex15) || RegexUtil.matches(idCardNo, regex18);
	}

	/**
	 * 验证字符串长度
	 * 
	 * @param str
	 *            字符串
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @return
	 */
	public static boolean checkLength(String str, int minLength, int maxLength) {
		String regex = "^.{" + minLength + "," + maxLength + "}$";
		return RegexUtil.matches(str, regex);
	}

	/**
	 * 验证字符串，只能包含字母、数字和常用符号
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean common(String str) {
		String regex = "^[\\x21-\\x7e]+$";
		return RegexUtil.matches(str, regex);
	}

	/**
	 * 验证字符串，只能包含中文、字母、数字、'-'、'_'
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean commonChinese(String str) {
		String regex = "^[\u4e00-\u9fa5\\w\\-]+$";
		return RegexUtil.matches(str, regex);
	}

	/**
	 * 验证字符串，只能包含数字
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean commonNumber(String str) {
		String regex = "^\\d+$";
		return RegexUtil.matches(str, regex);
	}

	/**
	 * 计算字符串的长度（一个双字节字符长度计2，ASCII字符计1）
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static int length(String str) {
		if (str == null) {
			return 0;
		}
		String newStr = str.replaceAll("[^\\x00-\\xff]", "12");
		return newStr.length();
	}
}