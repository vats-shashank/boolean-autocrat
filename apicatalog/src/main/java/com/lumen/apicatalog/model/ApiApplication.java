package com.lumen.apicatalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tjam_api_application")
public class ApiApplication {
	
	
	@Id
	@Column(name="APP_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long appId;
	
	@Column(name="TJAM_APP_NAME")
	private String appName;


	public Long getAppId() {
		return appId;
	}


	public void setAppId(Long appId) {
		this.appId = appId;
	}


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
	
}
