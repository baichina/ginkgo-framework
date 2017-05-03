package io.ginkgo.util;

import org.junit.Test;

public class UUIDUtilTest {

	@Test
	public void getUUID() {
		for (int i = 0; i < 10; i++) {
			System.out.println(UUIDUtil.getUUID());
		}
	}
}