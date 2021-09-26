package com.lumen.apicatalog.dao;



public class EmailRequest {
	String emailAddr;
	String swagger;
	String model;
	String apiName;
	
	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getSwagger() {
		return swagger;
	}

	public void setSwagger(String swagger) {
		this.swagger = swagger;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	@Override
	public String toString() {
		return "EmailRequest [emailAddr=" + emailAddr + ", swagger=" + swagger + ", model=" + model + 
				", apiName=" + apiName + "]";
				
		
	}
		
}
