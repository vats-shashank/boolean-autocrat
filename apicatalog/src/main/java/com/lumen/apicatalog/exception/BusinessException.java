package com.lumen.apicatalog.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{
	
	
	private static final long serialVersionUID = 8200342672178420227L;

	private String message;
	
	private HttpStatus httpStatus;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public BusinessException(String message, HttpStatus httpStatus) {
		
		this.message = message;
		this.httpStatus = httpStatus;
	}

	
	  public BusinessException() { 
		  
		  
		  
	  }
	 
	
	
	
}
