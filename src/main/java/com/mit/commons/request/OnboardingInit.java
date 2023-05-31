package com.mit.commons.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OnboardingInit {

	@NotNull
	private String idDevice;
}
