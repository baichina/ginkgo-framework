package io.ginkgo.util;

import org.junit.Test;

public class RandomUtilTest {

	@Test
	public void randomInt() {
		for (int i = 0; i < 10; i++) {
			System.out.println(RandomUtil.randomInt(1, 5));
		}
	}

	@Test
	public void randomStr4() {
		for (int i = 0; i < 10; i++) {
			System.out.println(RandomUtil.randomStr4());
		}
	}

	@Test
	public void randomStr5() {
		for (int i = 0; i < 10; i++) {
			System.out.println(RandomUtil.randomStr5());
		}
	}

	@Test
	public void randomStr6() {
		for (int i = 0; i < 10; i++) {
			System.out.println(RandomUtil.randomStr6());
		}
	}
}