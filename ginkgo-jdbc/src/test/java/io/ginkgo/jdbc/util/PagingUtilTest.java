package io.ginkgo.jdbc.util;

import org.junit.Test;

public class PagingUtilTest {

	private static final String sql = "select * from dual";

	@Test
	public void getPagingSql4Mysql() {
		System.out.println(PagingUtil.getPagingSql4Mysql(sql, 1, 10));
	}

	@Test
	public void getCountSql4Mysql() {
		System.out.println(PagingUtil.getCountSql4Mysql(sql));
	}
}