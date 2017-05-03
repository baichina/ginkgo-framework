package io.ginkgo.cache;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class RscacheTest {

	private static final Logger log = Logger.getLogger(RscacheTest.class);
	@Autowired
	private TestService testService;

	@Test
	public void test() {
		log.info("test=" + testService.getString("a"));
		log.info("test=" + testService.getString("a"));
	}
}