package io.ginkgo.jdbc;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BaseDaoTest {

	private static final Logger log = Logger.getLogger(BaseDaoTest.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	BaseDao baseDao = new BaseDao();

	@Test
	public void queryCount() {
		String sql = "select 1 from dual";
		log.info("queryCount=" + baseDao.queryCount(jdbcTemplate, sql));
	}
}