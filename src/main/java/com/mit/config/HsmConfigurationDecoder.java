package com.mit.config;

import org.apache.commons.configuration2.Configuration;

import com.mit.commons.util.HDEDecryptCipherHelper;
import com.mit.commons.util.HDEHelper2;



public class HsmConfigurationDecoder implements AbstractConfigurationDecoder {

	private HDEDecryptCipherHelper hdeHelper;

	@Override
	public String decode(String s) {
		s = hdeHelper.decode(s);
		return s;
	}

	@Override
	public void configure(Configuration config) {
		try {
//			new HDEHelper2();
//			hdeHelper = new HDEDecryptCipherHelper();
			hdeHelper = new HDEDecryptCipherHelper(config.getString("hde.key"), config.getString("hde.key.pass"),
					config.getString("hde.key.path.file"), config.getString("hde.key.pub"),
					config.getString("hde.key.pass.pub"), config.getString("hde.key.path.file.pub"),
					config.getString("hde.data"));
			hdeHelper.setConfiguration(config);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
