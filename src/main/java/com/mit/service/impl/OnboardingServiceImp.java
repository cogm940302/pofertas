package com.mit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import com.mit.commons.request.Client;
import com.mit.commons.request.OnboardingInit;
import com.mit.repository.OnboardingRepository;
import com.mit.service.OnboardingService;
import com.mit.util.exceptions.LogicException;

@Service
public class OnboardingServiceImp implements OnboardingService {

	private static final Logger log = LogManager.getLogger(OnboardingServiceImp.class);
	@Autowired
	private OnboardingRepository onboardingRepository;
	

	@Override
	public Map<String, Object> create(OnboardingInit data) {
		Map<String, Object> response = onboardingRepository.createOrUpdateClient(data.getIdDevice());
		return response;
	}

	@Override
	public Map<String, Object> accept(Client client) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			onboardingRepository.accept(client);	
		} catch (Exception e) {
			
			throw new LogicException("Error Saving Data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public List<Map<String, Object>> clientes() {
		return onboardingRepository.clientes();
	}


}
