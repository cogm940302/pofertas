package com.mit.commons.request;

import javax.validation.constraints.NotNull;

public class OnboardingInit {

	@NotNull
	private String idDevice;

	public String getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(String idDevice) {
		this.idDevice = idDevice;
	}
	
	
}
