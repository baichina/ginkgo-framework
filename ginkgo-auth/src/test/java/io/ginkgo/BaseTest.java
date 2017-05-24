package io.ginkgo;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 基础测试类
 * 
 * @author baiwei
 */
public class BaseTest {

	protected static final Logger log = Logger.getLogger(BaseTest.class);

	public void testPrint(Object obj) {
		log.info(JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue));
	}
}