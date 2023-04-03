package com.mit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mit.commons.request.Client;
import com.mit.commons.request.OnboardingInit;
import com.mit.service.OnboardingService;

@RestController
@RequestMapping("/onboarding")
@Validated
public class OnboardingController {

	private static final Logger log = LogManager.getLogger(OnboardingController.class);

	@Autowired
	private OnboardingService onboardingService;

	@PostMapping(value = "/terms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Valid
	public Map<String, Object> create(@Valid @RequestBody OnboardingInit data) {
		log.info("Estos son los datos: {}" , data);
		Map<String, Object> mapa = new HashMap<>();
		mapa = onboardingService.create(data);
		return mapa;
	}

	@PostMapping(value = "/accept", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Valid
	public Map<String, Object> accept(@Valid @RequestBody Client client) {
		log.info("Estos son los datos: {}" , client);
		return onboardingService.accept(client);
	}

	@GetMapping(value = "/clients", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Valid
	public List<Map<String, Object>> clientes() {
		List<Map<String, Object>> mapa = new ArrayList<>();
		mapa = onboardingService.clientes();
		return mapa;
	}
	
//	@PostMapping(value = "/init", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@Valid
//	public Map<String, Object> create2(@Valid @RequestBody Client datos) {
//		System.out.println(datos);
//		log.info("test del log");
//		Map<String, Object> mapa = new HashMap<>();
//		mapa.put("codigo", 200);
//		mapa.put("message", "Todo bien");
//		onboardingService.create(datos);
//		return mapa;
//	}
	
//	@GetMapping(value = "/terms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@Valid
//	public Map<String, Object> terms() {
//		Map<String, Object> mapa = new HashMap<>();
//		mapa.put("codigo", 200);
//		mapa.put("terms", "Estos son los terminos y condiciones");
//		return mapa;
//	}
}
