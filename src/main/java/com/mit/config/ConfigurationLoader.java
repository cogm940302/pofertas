package com.mit.config;

import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationDecoder;
import org.apache.commons.configuration2.builder.BuilderConfigurationWrapperFactory;
import org.apache.commons.configuration2.builder.BuilderConfigurationWrapperFactory.EventSourceSupport;
import org.apache.commons.configuration2.builder.ConfigurationBuilder;
import org.apache.commons.configuration2.builder.combined.ReloadingCombinedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigurationLoader {
	private static BuilderConfigurationWrapperFactory wrapper;
	private static ConfigurationBuilder<? extends Configuration> builder;

	private static int initialDelay = 60;
	private static int period = 30;
	private static TimeUnit timeUnit = TimeUnit.MINUTES;
	private static ConfigurationDecoder decoder;

	private ConfigurationLoader() {
	}

	public static synchronized Configuration getInstance(String configFile, ConfigurationDecoder decoder) {
		if (builder == null) {
			ConfigurationLoader.decoder = decoder;
			loadProperties(configFile);
			wrapper = new BuilderConfigurationWrapperFactory(EventSourceSupport.BUILDER);
		}
		return wrapper.createBuilderConfigurationWrapper(Configuration.class, builder);
	}

	/**
	 * Carga los archivos de propiedades especificados en <b>configFactoryFile</b>
	 * 
	 * @param configFactoryFile ruta en donde se encuentra el archivo de propiedades
	 *                          a cargar.
	 * @return Configuration con las propiedades de los archivos especificados.
	 * @throws ConfigurationException
	 */
	private static void loadProperties(String configFactoryFile) {
		Parameters params = new Parameters();
		URL resource = ConfigurationLoader.class.getClassLoader().getResource(configFactoryFile);
		if (resource != null && period > 0) {
			ReloadingCombinedConfigurationBuilder rcBuilder = new ReloadingCombinedConfigurationBuilder()
					.configure(params.fileBased().setURL(resource).setConfigurationDecoder(decoder));
			ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
			scheduler.scheduleAtFixedRate(() -> rcBuilder.getReloadingController().checkForReloading(null),
					initialDelay, period, timeUnit);
			builder = rcBuilder;
		}
	}
}
