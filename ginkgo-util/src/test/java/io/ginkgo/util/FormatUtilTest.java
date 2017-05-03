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
	public void decimalToStr() {
		System.out.println(FormatUtil.decimalToStr(pi, FormatUtil.DECIMAL_2));
	}

	@Test
	public void strToDecimal() {
		System.out.println(FormatUtil.decimalToStr(pi.toString(), FormatUtil.DECIMAL_2));
	}
}