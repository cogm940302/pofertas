package com.mit.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.mit.commons.request.MailNotification;
import com.mit.util.mail.MailServ;
import com.mit.util.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

	@Autowired
	private MailService mailService;

	@RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}
	
	@GetMapping(value="/test")
	public String test() {
		return "si funciono";
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE,path = "/mail")
	public ResponseEntity<Boolean> testMail(@RequestBody MailNotification mailNotification){
		Optional<Boolean> result = mailService.normalSending(mailNotification);
		return ResponseEntity.ok(result.orElse(Boolean.FALSE));
	}
}
