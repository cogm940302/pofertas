package com.mit.controller;

import java.util.List;

import javax.validation.Valid;

import com.mit.dto.request.ClientRequest;
import com.mit.dto.response.ClientResponse;
import com.mit.dto.response.OnboardinResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mit.commons.request.OnboardingInit;
import com.mit.service.OnboardingService;

@RestController
@RequestMapping(value = "/onboarding")
@Validated
@Slf4j
public class OnboardingController {

	private final OnboardingService service;

	public OnboardingController(OnboardingService service) {
		this.service = service;
	}

	@PostMapping(value = "/terms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Valid
	public ResponseEntity<OnboardinResponse> create(@Valid @RequestBody OnboardingInit data) {
		log.info("Estos son los datos: {}" , data);
		return ResponseEntity.ok(this.service.create(data));
	}

	@PutMapping(value = "/accept/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updatePOFER02Client(
			@PathVariable String id,
			@Valid @RequestBody ClientRequest request
	) {
		this.service.accept(id, request);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClientResponse>> clientes() {
		List<ClientResponse> clients = service.clientes();
		return ResponseEntity.ok(clients);
	}
}
