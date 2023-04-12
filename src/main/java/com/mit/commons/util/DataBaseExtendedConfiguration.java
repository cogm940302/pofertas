package com.mit.commons.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.configuration2.DatabaseConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataBaseExtendedConfiguration extends DatabaseConfiguration {

	private String jndi;
	private DataSource datasource;

	private String driverClass;
	private String jdbcUrl;
	private String password;
	private String user;

	/**
	 * @return the jndi
	 */
	public String getJndi() {
		return jndi;
	}

	@Override
	public DataSource getDatasource() {
		try {
			if (datasource == null) {
				if (jndi != null) {
					Context ctx = new InitialContext();
					datasource = (DataSource) ctx.lookup(jndi);
				} else {
					if (driverClass != null) {
						DriverManagerDataSource dmds = new DriverManagerDataSource(jdbcUrl, user, password);
						datasource = dmds;
					}
				}
			}
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
		return datasource;
	}

	/**
	 * @param jndi the jndi to set
	 */
	public void setJndi(String jndi) {
		this.jndi = jndi;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setReloadingRefreshDelay(Long reloadingRefreshDelay) {
		/**setReloadingRefreshDelay**/
	}

	public void setTimeUnit(String timeUnit) {
		/**setTimeUnit**/
	}
}
