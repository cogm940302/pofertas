package com.mit.service;

import java.util.List;
import java.util.Map;

import com.mit.commons.request.Client;
import com.mit.commons.request.OnboardingInit;

public interface OnboardingService {

	Map<String, Object> create(OnboardingInit data);
	Map<String, Object> accept(Client data);
	List< Map<String, Object> > clientes();
}
