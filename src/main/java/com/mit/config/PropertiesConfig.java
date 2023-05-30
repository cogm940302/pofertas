package com.mit.config;

import org.apache.commons.configuration2.spring.ConfigurationPropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;


@Configuration
public class PropertiesConfig {

//	@Bean
//	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws Exception {
//		ConfigurationPropertiesFactoryBean cpFactoryBean = new ConfigurationPropertiesFactoryBean(config2());
//		PropertyPlaceholderConfigurer placeHolder = new PropertyPlaceholderConfigurer();
//		System.out.println(cpFactoryBean.getObject());
//		placeHolder.setProperties(cpFactoryBean.getObject());
//		System.out.println(cpFactoryBean.getObject());
//		return placeHolder;
//	}
	
	@Bean
	@DependsOn("dataSource")
//	@Lazy
	public org.apache.commons.configuration2.Configuration config2() throws IOException {
		System.out.println("************+configuration");
		HsmConfigurationDecoder decoder = new HsmConfigurationDecoder();
		org.apache.commons.configuration2.Configuration config = ConfigurationLoader
				.getInstance("config.xml", decoder);
		decoder.configure(config);
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
