package com.mit.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.mit.domain.POFER02Client;
import com.mit.dto.request.ClientRequest;
import com.mit.dto.response.ClientResponse;
import com.mit.dto.response.OnboardinResponse;
import com.mit.repository.IOnboardingRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mit.commons.request.OnboardingInit;
import com.mit.service.OnboardingService;

@Service
@Slf4j
public class OnboardingServiceImp implements OnboardingService {
	private final IOnboardingRepository repository;
	private final ModelMapper modelMapper;

	@Autowired
	public OnboardingServiceImp(
			IOnboardingRepository repository,
			ModelMapper modelMapper) {
		this.repository = repository;
		this.modelMapper = modelMapper;
	}

	@Override
	public OnboardinResponse create(OnboardingInit data) {
		POFER02Client entity;
		OnboardinResponse response = new OnboardinResponse();
		Optional<POFER02Client> optional = this.repository.findById(data.getIdDevice());

		if (optional.isPresent()) {
			entity = optional.get();
			entity.setUpdateDate(new Date());
			response.setTerms(entity.getTerms() == 1);
		} else {
			entity = new POFER02Client();
			entity.setId(UUID.randomUUID().toString());
			entity.setIdDevice(data.getIdDevice());
			entity.setTerms(0);
			entity.setCreationDate(new Date());
			entity.setUpdateDate(new Date());
			response.setTerms(Boolean.FALSE);
		}
		entity = this.repository.save(entity);
		response.setKey(entity.getId());
		return response;
	}

	@Override
	public void accept(String id, ClientRequest request) {
		Optional<POFER02Client> optionalClient = repository.findById(id);

		if (optionalClient.isPresent()) {
			POFER02Client client = optionalClient.get();
			client.setVersion(request.getVersion());
			client.setModel(request.getModel());
			client.setBranch(request.getBranch());
			client.setCompany(request.getCompany());
			client.setUsername(request.getUsername());
			client.setAfiliacion(request.getAfiliacion());
			client.setTerms(1);
			client.setUpdateDate(new Date());

			repository.save(client);
		} else {
			throw new RuntimeException("POFER02Client not found with id: " + id); // TODO Implementar el Exception con ControlAdvidcer
		}
	}

	@Override
	public List<ClientResponse> clientes() {
		List<POFER02Client> clients = repository.findAll();
		return clients.stream()
				.map(client -> modelMapper.map(client, ClientResponse.class))
				.collect(Collectors.toList());
	}

}
