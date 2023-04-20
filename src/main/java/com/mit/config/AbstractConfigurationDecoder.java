package com.mit.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationDecoder;

public interface AbstractConfigurationDecoder extends ConfigurationDecoder {

	public void configure(Configuration config);
}
