package io.ginkgo.util;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RegexValidatorTest {

	@Test
	public void isEmail() {
		assertTrue(RegexValidator.isEmail("1@qq.com"));
	}

	@Test
	public void isMobileNo() {
		assertTrue(RegexValidator.isMobileNo("13800000000"));
	}

	@Test
	public void isIdCardNo() {
		assertTrue(RegexValidator.isIdCardNo("340811199901014111"));
	}

	@Test
	public void checkLength() {
		assertTrue(RegexValidator.checkLength("123", 2, 5));
	}

	@Test
	public void common() {
		assertTrue(RegexValidator.common("abc"));
	}

	@Test
	public void commonChinese() {
		assertTrue(RegexValidator.commonChinese("都是"));
	}

	@Test
	public void commonNumber() {
		assertTrue(RegexValidator.commonNumber("2323"));
	}

	@Test
	public void length() {
		System.out.println(RegexValidator.length("都是123"));
	}
}