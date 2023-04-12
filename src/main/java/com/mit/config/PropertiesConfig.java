package com.mit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;


@Configuration
public class PropertiesConfig {

	@Bean
//	@DependsOn("dataSource")
//	@Lazy
	public org.apache.commons.configuration2.Configuration config2() {
		System.out.println("************+configuration");
		org.apache.commons.configuration2.Configuration config = ConfigurationLoader
				.getInstance("./config.xml", null);
		return config;
	}

//	@Bean("configHDE")
//	@DependsOn("dataSource")
//	@Lazy
//	public org.apache.commons.configuration2.Configuration configHDE() {
//		System.out.println("************+configuration");
//		org.apache.commons.configuration2.Configuration config = ConfigurationLoader
//				.getInstance("config.xml", null);
//		return config;
//	}
}
