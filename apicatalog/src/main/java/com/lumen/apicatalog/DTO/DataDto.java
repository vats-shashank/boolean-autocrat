package com.lumen.apicatalog.DTO;

public class DataDto {
	
	private String message;
	
	private String status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message,String status) {
		this.message = message;
	}

	public DataDto(String message,String status) {
		super();
		this.message = message;
		this.status=status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
