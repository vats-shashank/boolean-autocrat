package com.lumen.apicatalog.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class ApiCatagory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "database_seq_generator")
	private long apiCatagoryId;
	
	private String apiCatagoryName;
	
	private Date createdDate;

	public long getApiCatagoryId() {
		return apiCatagoryId;
	}

	public void setApiCatagoryId(long apiCatagoryId) {
		this.apiCatagoryId = apiCatagoryId;
	}

	public String getApiCatagoryName() {
		return apiCatagoryName;
	}

	public void setApiCatagoryName(String apiCatagoryName) {
		this.apiCatagoryName = apiCatagoryName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
