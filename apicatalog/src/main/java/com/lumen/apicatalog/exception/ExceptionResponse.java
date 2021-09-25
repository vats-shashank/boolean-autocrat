package com.lumen.apicatalog.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
	
	private String message;
	
	private LocalDateTime timpeStamp;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimpeStamp() {
		return timpeStamp;
	}

	public void setTimpeStamp(LocalDateTime timpeStamp) {
		this.timpeStamp = timpeStamp;
		
	}

	
	
	

}
