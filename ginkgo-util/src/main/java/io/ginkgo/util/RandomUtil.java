package io.ginkgo.util;

/**
 * 生成随机字符串工具类
 * 
 * @since 1.0.0
 * @author Barry
 */
public class RandomUtil {

	/**
	 * 随机生成一个大于等于最小数，且小于等于最大数的整数
	 * 
	 * @param min
	 *            最小数
	 * @param max
	 *            最大数
	 * @return
	 */
	public static int randomInt(int min, int max) {
		int randNum = min + (int) (Math.random() * ((max - min) + 1));
		return randNum;
	}

	/**
	 * 随机生成4位数的字符串[0000,9999]
	 * 
	 * @return
	 */
	public static String randomStr4() {
		return String.format("%04d", randomInt(0, 9999));
	}

	/**
	 * 随机生成5位数的字符串[00000,99999]
	 * 
	 * @return
	 */
	public static String randomStr5() {
		return String.format("%05d", randomInt(0, 99999));
	}

	/**
	 * 随机生成6位数的字符串[000000,999999]
	 * 
	 * @return
	 */
	public static String randomStr6() {
		return String.format("%06d", randomInt(0, 999999));
	}
}