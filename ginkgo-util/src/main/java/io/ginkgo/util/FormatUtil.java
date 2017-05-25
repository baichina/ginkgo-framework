package io.ginkgo.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化工具类
 * <p>
 * 时间、小数
 * </p>
 * 
 * @since 1.0.0
 * @author Barry
 */
public class FormatUtil {
	/**
	 * 时间格式：年月日时分秒
	 * <p>
	 * eg.20170428151515
	 * </p>
	 */
	public static final String DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 时间格式：年-月-日 时:分:秒
	 * <p>
	 * eg.2017-04-28 15:15:15
	 * </p>
	 */
	public static final String DATE_YYYYMMDDHHMMSS_1 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式：年月/日/时/
	 * <p>
	 * eg.201704/28/15/
	 * </p>
	 */
	public static final String DATE_YYYYMMDDHH = "yyyyMM/dd/HH/";

	/**
	 * 时间格式：年月日
	 * <p>
	 * eg.20170428
	 * </p>
	 */
	public static final String DATE_YYYYMMDD = "yyyyMMdd";

	/**
	 * 时间格式：年-月-日
	 * <p>
	 * eg.2017-04-28
	 * </p>
	 */
	public static final String DATE_YYYYMMDD_1 = "yyyy-MM-dd";

	/**
	 * 时间格式：月日时
	 * <p>
	 * eg.042815
	 * </p>
	 */
	public static final String DATE_MMDDHH = "MMddHH";

	/**
	 * 时间格式：月日
	 * <p>
	 * eg.0428
	 * </p>
	 */
	public static final String DATE_MMDD = "MMdd";

	/**
	 * 时间格式：月-日
	 * <p>
	 * eg.04-28
	 * </p>
	 */
	public static final String DATE_MMDD_1 = "MM-dd";

	/**
	 * 时间格式：时分秒
	 * <p>
	 * eg.151515
	 * </p>
	 */
	public static final String DATE_HHMMSS = "HHmmss";

	/**
	 * 时间格式：时:分:秒
	 * <p>
	 * eg.15:15:15
	 * </p>
	 */
	public static final String DATE_HHMMSS_1 = "HH:mm:ss";

	/**
	 * 小数格式：小数点后保留0位
	 * <p>
	 * eg.1
	 * </p>
	 */
	public static final String DECIMAL_0 = "0";

	/**
	 * 小数格式：小数点后保留2位
	 * <p>
	 * eg.1.00
	 * </p>
	 */
	public static final String DECIMAL_2 = "0.00";

	/**
	 * 小数格式：小数点后保留4位
	 * <p>
	 * eg.1.0000
	 * </p>
	 */
	public static final String DECIMAL_4 = "0.0000";

	/**
	 * 时间格式化为时间字符串
	 * 
	 * @param date
	 *            时间
	 * @param formatStr
	 *            格式
	 * @return
	 */
	public static String dateToStr(Date date, String formatStr) {
		DateFormat format = new SimpleDateFormat(formatStr);
		return format.format(date);
	}

	/**
	 * 字符串格式化为时间
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @param formatStr
	 *            格式
	 * @return
	 */
	public static Date strToDate(String dateStr, String formatStr) {
		DateFormat format = new SimpleDateFormat(formatStr);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 小数格式化为小数字符串
	 * <p>
	 * 舍去的部分四舍五入
	 * </p>
	 * 
	 * @param number
	 *            小数
	 * @param formatStr
	 *            格式
	 * @return
	 */
	public static String getDecimalString(Number number, String formatStr) {
		DecimalFormat format = new DecimalFormat(formatStr);
		return format.format(number);
	}

	/**
	 * 小数字符串格式化为小数字符串
	 * <p>
	 * 舍去的部分四舍五入
	 * </p>
	 * 
	 * @param numberStr
	 *            小数字符串
	 * @param formatStr
	 *            格式
	 * @return
	 */
	public static String getDecimalString(String numberStr, String formatStr) {
		DecimalFormat format = new DecimalFormat(formatStr);
		try {
			return format.format(format.parse(numberStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 小数格式化为小数
	 * <p>
	 * 舍去的部分四舍五入
	 * </p>
	 * 
	 * @param number
	 *            小数
	 * @param formatStr
	 *            格式
	 * @return
	 */
	public static Number getDecimal(Number number, String formatStr) {
		DecimalFormat format = new DecimalFormat(formatStr);
		try {
			return format.parse(number.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 小数字符串格式化为小数
	 * <p>
	 * 舍去的部分四舍五入
	 * </p>
	 * 
	 * @param numberStr
	 *            小数字符串
	 * @param formatStr
	 *            格式
	 * @return
	 */
	public static Number getDecimal(String numberStr, String formatStr) {
		DecimalFormat format = new DecimalFormat(formatStr);
		try {
			return format.parse(numberStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}