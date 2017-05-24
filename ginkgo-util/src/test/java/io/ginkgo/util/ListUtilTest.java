package io.ginkgo.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListUtilTest {

	@Test
	public void fetch() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 100; i++) {
			list.add(i);
		}

		ListUtil<Integer> lu = new ListUtil<Integer>();
		for (int i = -1; i <= 101; i++) {
			System.out.print(i + " ");
			System.out.println(lu.fetch(list, i));
		}
	}
}