package com.mit.service;

import java.util.List;
import java.util.Map;

import com.mit.commons.request.Client;
import com.mit.commons.request.OnboardingInit;
import com.mit.dto.request.ClientRequest;
import com.mit.dto.response.ClientResponse;
import com.mit.dto.response.OnboardinResponse;

public interface OnboardingService {

	OnboardinResponse create(OnboardingInit data);
	void accept(String id, ClientRequest request);
	List<ClientResponse> clientes();
}
