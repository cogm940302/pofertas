package com.mit.commons.util;

import java.util.Map;

import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.BuilderParameters;
import org.apache.commons.configuration2.reloading.ReloadingController;
import org.apache.commons.configuration2.reloading.ReloadingControllerSupport;

public class ReloadingDataBaseConfigurationBuilder<T extends /* DatabaseConfiguration */ DataBaseExtendedConfiguration>
		extends BasicConfigurationBuilder<T> implements ReloadingControllerSupport {

	private ReloadingController reloadingController;
	private DataBaseReloadDetector reloadingDetector;

	public ReloadingDataBaseConfigurationBuilder(Class<? extends T> resCls) {
		super(resCls);
		reloadingController = createReloadingController();
	}

	public ReloadingDataBaseConfigurationBuilder(Class<? extends T> resCls, Map<String, Object> params) {
		this(resCls, params, false);
		reloadingController = createReloadingController();
	}

	public ReloadingDataBaseConfigurationBuilder(Class<? extends T> resCls, Map<String, Object> params,
			boolean allowFailOnInit) {
		super(resCls, params, false);
		reloadingController = createReloadingController();
	}

	private ReloadingController createReloadingController() {
		reloadingDetector = new DataBaseReloadDetector();
		ReloadingController ctrl = new ReloadingController(reloadingDetector);
		connectToReloadingController(ctrl);
		return ctrl;
	}

	@Override
	public ReloadingController getReloadingController() {
		return reloadingController;
	}

	@Override
	public BasicConfigurationBuilder<T> configure(BuilderParameters... params) {
		BasicConfigurationBuilder<T> bcb = super.configure(params);
		reloadingDetector.configureParams(getParameters());
		return bcb;
	}
}
