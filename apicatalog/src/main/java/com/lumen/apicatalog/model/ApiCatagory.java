package com.lumen.apicatalog.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tjam_api_category")
public class ApiCatagory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="CATEGORY_ID")
	private long apiCatagoryId;
	
	@Column(name="CATEGORY_NAME")
	private String apiCatagoryName;
	
	@Column(name="CREATE_DATE")
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
