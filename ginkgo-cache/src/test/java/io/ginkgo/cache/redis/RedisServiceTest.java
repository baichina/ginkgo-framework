package io.ginkgo.cache.redis;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.ginkgo.App;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class RedisServiceTest {

	private static final Logger log = Logger.getLogger(RedisServiceTest.class);

	@Autowired
	private RedisService<String> redisService;

	@Test
	public void set() {
		log.info("set");
		redisService.set("test:1", "aaa");
	}

	@Test
	public void get() {
		log.info("get=" + redisService.get("test:1"));
	}
}