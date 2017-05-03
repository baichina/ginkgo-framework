package io.ginkgo.util;

import org.junit.Test;

public class MD5UtilTest {

	@Test
	public void encode() {
		System.out.println(MD5Util.encode("12345678"));
	}

	@Test
	public void encodePassword() {
		System.out.println(MD5Util.encodePassword("12345678"));
	}
}