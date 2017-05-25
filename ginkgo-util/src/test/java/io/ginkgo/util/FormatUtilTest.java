package io.ginkgo.util;

import java.util.Date;

import org.junit.Test;

public class FormatUtilTest {

	private static final Double pi = 3.141592654;

	@Test
	public void dateToStr() {
		System.out.println(FormatUtil.dateToStr(new Date(), FormatUtil.DATE_YYYYMMDDHHMMSS_1));
	}

	@Test
	public void strToDate() {
		System.out.println(FormatUtil.strToDate("0411", FormatUtil.DATE_MMDD));
	}

	@Test
	public void getDecimal() {
		System.out.println(FormatUtil.getDecimal(pi, FormatUtil.DECIMAL_2));
		System.out.println(FormatUtil.getDecimal(pi.toString(), FormatUtil.DECIMAL_2));
	}

	@Test
	public void getDecimalString() {
		System.out.println(FormatUtil.getDecimalString(pi, FormatUtil.DECIMAL_2));
		System.out.println(FormatUtil.getDecimalString(pi.toString(), FormatUtil.DECIMAL_2));
	}
}