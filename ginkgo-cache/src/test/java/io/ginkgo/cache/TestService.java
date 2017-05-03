package io.ginkgo.cache;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import io.ginkgo.cache.GinkgoCache;

@Service
public class TestService {

	private static final Logger log = Logger.getLogger(TestService.class);

	@GinkgoCache(key = "test:TestService:%s", expr = "#s")
	public String getString(String s) {
		log.info("not cache getString=" + s);
		return s;
	}

	@GinkgoCache(key = "test:TestService:%s", expr = "#o")
	public Object getObject(String o) {
		Object r = new Object();
		log.info("not cache getObject=" + r);
		return r;
	}
}