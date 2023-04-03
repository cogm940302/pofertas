package com.mit.util.exceptions;

import org.springframework.http.HttpStatus;

public class LogicException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8862240721581322733L;
	private HttpStatus httpStatus;
	private String subcodigo;
	private String descripcion;

	public LogicException() {
		super();
	}

	public LogicException(String arg0) {
		super(arg0);
	}

	public LogicException(String arg0, HttpStatus status) {
		super(arg0);
		httpStatus = status;
	}

	public LogicException(String arg0, HttpStatus status, String subcodigo) {
		super(arg0);
		httpStatus = status;
		this.subcodigo = subcodigo;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getSubcodigo() {
		return subcodigo;
	}

	public void setSubcodigo(String subcodigo) {
		this.subcodigo = subcodigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
