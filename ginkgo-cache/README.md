# ginkgo-cache
一个基于redis的java缓存， 你只需要为方法添加一行注解，即可将数据缓存到redis。<br/>
示例， 
```Java
	// 需要缓存的方法 
	@GinkgoCache(key = "test:TestService:%s", expr = "#s")
	public String getString(String s) {
		log.info("not cache getString=" + s);
		return s;
	}

	// 测试方法
	@Test
	public void test() {
		log.info("test=" + testService.getString("a"));
		log.info("test=" + testService.getString("a"));
	}
```
测试方法中， <br/>
第一次调用，将执行该方法，并将结果缓存到redis， <br/>
第二次调用，不执行该方法，返回缓存中的数据。
