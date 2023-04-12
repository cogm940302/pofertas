package com.mit.commons.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.reloading.ReloadingDetector;

public class DataBaseReloadDetector implements ReloadingDetector {

	private Date lastUpdated;
	private TimeUnit timeUnit;
	private Long delay;

	public DataBaseReloadDetector() {
		lastUpdated = new Date();
		timeUnit = TimeUnit.HOURS;
		delay = 1L;
	}

	@Override
	public boolean isReloadingRequired() {
		Calendar next = Calendar.getInstance();
		next.setTime(lastUpdated);
		Long durationL = timeUnit.toMillis(delay);
		next.add(Calendar.MILLISECOND, durationL.intValue());
		return new Date().after(next.getTime());
	}

	@Override
	public void reloadingPerformed() {
		lastUpdated = new Date();
	}

	public void configureParams(Map<String, Object> params) {
		delay = (Long) params.get("reloadingRefreshDelay");
		timeUnit = (TimeUnit) params.get("timeUnit");
	}

}
