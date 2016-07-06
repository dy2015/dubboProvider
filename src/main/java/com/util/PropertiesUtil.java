package com.util;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.core.io.ClassPathResource;

import com.common.collector.Ec;

/**
 * 配置文件工具类
 *
 * @date 2016年6月30日
 * @author
 */
public class PropertiesUtil {
	private static final String kafkaConfigPath = "kafka.properties";
	private static final String configPath = "config.properties";
	public static PropertiesUtil I_CENTER = new PropertiesUtil(kafkaConfigPath);
	public static PropertiesUtil I_CONFIG = new PropertiesUtil(configPath);
	private PropertiesConfiguration config;

	public PropertiesUtil(String config) {
		init(config);
	}

	private void init(String config) {
		try {
			this.config = new PropertiesConfiguration();
			// 设置properties文件中的value不需要分隔符","分隔
			this.config.setDelimiterParsingDisabled(true);
			this.config.load(new ClassPathResource(config).getFile());
		} catch (Exception e) {
			Ec.COMMON.log("配置文读取初始化失败: " + config, e, Ec.DIM_COMMON_ERR);
		}
	}

	public String get(String key) {
		return config.getString(key);
	}

	public String[] getValue(String key) {
		String value = config.getString(key);
		if (value == null || value.trim().length() <= 0) {
			return new String[] {};
		}
		return value.split(",");
	}

}
