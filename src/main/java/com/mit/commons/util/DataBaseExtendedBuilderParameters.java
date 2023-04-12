package com.mit.commons.util;

import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.builder.DatabaseBuilderParametersImpl;

public class DataBaseExtendedBuilderParameters extends DatabaseBuilderParametersImpl {

	/** Constant for the jndi property. */
	private static final String JNDI = "jndi";
	private static final String DRIVERCLASS = "driverClass";
	private static final String JDBCURL = "jdbcUrl";
	private static final String PASSWORD = "password";
	private static final String USER = "user";
	private static final String RELOADINGREFRESHDELAY = "reloadingRefreshDelay";
	private static final String TIMEUNIT = "timeUnit";

	public DatabaseBuilderParametersImpl setJndi(String jndi) {
		storeProperty(JNDI, jndi);
		return this;
	}

	public DatabaseBuilderParametersImpl setDriverClass(String driverClass) {
		storeProperty(DRIVERCLASS, driverClass);
		return this;
	}

	public DatabaseBuilderParametersImpl setJdbcUrl(String jdbcurl) {
		storeProperty(JDBCURL, jdbcurl);
		return this;
	}

	public DatabaseBuilderParametersImpl setPassword(String password) {
		storeProperty(PASSWORD, password);
		return this;
	}

	public DatabaseBuilderParametersImpl setUser(String user) {
		storeProperty(USER, user);
		return this;
	}

	public DatabaseBuilderParametersImpl setReloadingRefreshDelay(Long reloadingRefreshDelay) {
		storeProperty(RELOADINGREFRESHDELAY, reloadingRefreshDelay);
		return this;
	}

	public DatabaseBuilderParametersImpl setTimeUnit(String timeUnit) {
		storeProperty(TIMEUNIT, TimeUnit.valueOf(timeUnit));
		return this;
	}

}
