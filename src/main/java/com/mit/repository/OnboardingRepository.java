package com.mit.repository;

import java.util.List;
import java.util.Map;

import com.mit.commons.request.Client;

public interface OnboardingRepository {

	Map<String, Object> createOrUpdateClient(String idDevice);
	void accept(Client client);
	List< Map<String, Object> > clientes();
}
