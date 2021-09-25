package com.lumen.apicatalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BusinessException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5417951331828686001L;

	public BusinessException(HttpStatus status,String message, Throwable cause) {
        super(message, cause);
    }

	
	public BusinessException(HttpStatus status,String message) {
        super(message);
    }

}