package com.lumen.apicatalog.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tjam_api_model")
public class ApiModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="MODEL_ID")
	private Long modelId;
	
	@Column(name="CREATE_DATE")
	private Date creDate;
	

	@Column(name="STATUS")
	private String status;
	
	@Column(name="MODEL_NAME")
	private String modelName;
	
	@Column(name="MODEL_TYPE")
	private String modelType;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="apiId")
	private ApiCatalogInfo apiCatalogInfo;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Date getCreDate() {
		return creDate;
	}

	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public ApiCatalogInfo getApiCatalogInfo() {
		return apiCatalogInfo;
	}

	public void setApiCatalogInfo(ApiCatalogInfo apiCatalogInfo) {
		this.apiCatalogInfo = apiCatalogInfo;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	
	
	

}
