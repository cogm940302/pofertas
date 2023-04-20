package com.mit.config;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;


@Configuration
public class PersistenceConfig {

//	@Resource
//	private DataSource dataSource;

	@Bean(name = "dataSource")
	protected DataSource stubDataSource() {
		System.out.println("************ ire por la BD");
		DataSource ds = new EmbeddedDatabaseBuilder().setName("mitcua").addScript("classpath:schema.sql")
				.build();
		System.out.println("************ ya fui por la BD");
		System.out.println(ds);
		return ds;
	}

//	@Bean(name = "dataSource", destroyMethod = "softResetAllUsers")
//	@Profile("stub")
//	protected DataSource prodDataSource() {
//		DataSource ds = null;
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/axapci");
//		} catch (NamingException e) {
//			throw new IllegalArgumentException(e.getMessage());
//		}
//		return ds;
//	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataDource) {
		return new JdbcTemplate(stubDataSource());
	}
}
