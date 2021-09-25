package com.lumen.apicatalog.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BusinessExceptionHandling extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleExceptions(BusinessException exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimpeStamp(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,exception.getHttpStatus());
        return entity;
    }

}
