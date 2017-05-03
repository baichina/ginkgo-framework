package io.ginkgo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式处理字符串工具类
 * <p>
 * 匹配，分割，替换，查找
 * </p>
 * 
 * @since 1.0.0
 * @author Barry
 */
public class RegexUtil {

	/**
	 * 匹配
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            字符串
	 * @return 匹配结果
	 */
	public static Boolean matches(String str, String regex) {
		if (regex == null || str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 分割
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            字符串
	 * @return 分割后的字符串数组
	 */
	public static String[] split(String str, String regex) {
		if (regex == null || str == null) {
			return new String[0];
		}
		return str.split(regex);
	}

	/**
	 * 替换
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            字符串
	 * @param replacement
	 *            替换字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAll(String str, String regex, String replacement) {
		if (regex == null || str == null || replacement == null) {
			return "";
		}
		return str.replaceAll(regex, replacement);
	}

	/**
	 * 查找
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            字符串
	 * @return 查找到的字符串数组
	 */
	public static String[] find(String str, String regex) {
		if (regex == null || str == null) {
			return new String[0];
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		List<String> list = new ArrayList<>();
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list.toArray(new String[list.size()]);
	}
}