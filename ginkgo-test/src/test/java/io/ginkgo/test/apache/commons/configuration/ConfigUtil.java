package io.ginkgo.test.apache.commons.configuration;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

// 未测试成功，缺少依赖包
public class ConfigUtil {
	public static void main(String[] args) {
		Configurations configs = new Configurations();
		try {
			Configuration config = configs.properties(new File("config-a.properties"));
			String username = config.getString("username");
			System.out.println(username);
		} catch (ConfigurationException cex) {
		}
	}
}