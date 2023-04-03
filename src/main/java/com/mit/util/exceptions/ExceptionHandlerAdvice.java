package com.mit.util.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerAdvice {

	private static final String ERROR_DESCRIPTION = "%s :  %s. ";

	protected MessageSource messageSource;

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String,Object>> processValidationError(MethodArgumentNotValidException e) {
		Map<String, Object> response = new HashMap<>();
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		StringBuilder sb = new StringBuilder();
		for (FieldError error : errors) {
			String[] errorFieldName = error.getField().split("\\.");
			sb.append(String.format(ERROR_DESCRIPTION, errorFieldName[errorFieldName.length-1], error.getDefaultMessage()));
		}
		response.put("message",sb.toString());
		response.put("code", -20);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(LogicException.class)
	public ResponseEntity<Map<String,Object>> logicException(LogicException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", e.getMessage());
		response.put("code", e.getSubcodigo() != null ? e.getSubcodigo() : -3 );
		return new ResponseEntity<>(response, e.getHttpStatus());
	}



}
