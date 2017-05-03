package io.ginkgo.util;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class RegexUtilTest {

	@Test
	public void matches() {
		assertTrue(RegexUtil.matches("13812345678", "\\w{11}"));
	}

	@Test
	public void split() {
		String[] arrays = RegexUtil.split("a,b,c,", ",");
		assertTrue(arrays.length == 3);
		for (int i = 0; i < arrays.length; i++) {
			System.out.println(arrays[i]);
		}
	}

	@Test
	public void replaceAll() {
		System.out.println(RegexUtil.replaceAll(",a,b,c,", ",", ";"));
	}

	@Test
	public void find() {
		String[] arrays = RegexUtil.find(",apple,ban,me,", "\\w+");
		assertTrue(arrays.length == 3);
		for (int i = 0; i < arrays.length; i++) {
			System.out.println(arrays[i]);
		}
	}
}