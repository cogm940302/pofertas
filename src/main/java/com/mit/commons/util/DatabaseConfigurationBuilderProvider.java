package com.mit.commons.util;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.configuration2.builder.combined.BaseConfigurationBuilderProvider;

public class DatabaseConfigurationBuilderProvider extends BaseConfigurationBuilderProvider {

	public DatabaseConfigurationBuilderProvider() {
		super("org.apache.commons.configuration2.builder.BasicConfigurationBuilder",
				ReloadingDataBaseConfigurationBuilder.class.getName(), DataBaseExtendedConfiguration.class.getName(),
				Collections.singleton(DataBaseExtendedBuilderParameters.class.getName()));
	}

	public DatabaseConfigurationBuilderProvider(String bldrCls, String reloadBldrCls, String configCls,
			Collection<String> paramCls) {
		super(bldrCls, reloadBldrCls, configCls, paramCls);
	}

}
