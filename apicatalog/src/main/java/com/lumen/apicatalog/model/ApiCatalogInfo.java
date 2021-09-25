package com.lumen.apicatalog.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TJAM_API_INFO")
public class ApiCatalogInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="API_ID")
	private Long apiId;
	
	@Column(name="API_NAME")
	private String apiName;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="API_DESC")
	private String apiDescription;

	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="APP_ID")
	private ApiApplication apiApplication;
	
	@Column(name="SWAGGER_URL")
	private String apiSwagUrl;
	
    @Column(name="CREATE_DATE")
    private Date createdDate;
    
    @Column(name="MODIFIED_DATE")
    private Date updatedDate;
   
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private ApiCatagory apiCatagory; 
    
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
	@JoinColumn(name="USER_ID")
    private UserProfile userProfile;
    
  
	@OneToMany(mappedBy = "apiCatalogInfo",fetch = FetchType.LAZY)
    private List<ApiModel> apiModels;
    
	public ApiCatalogInfo() {
		super();
	}
	
	

	public ApiCatalogInfo(long apiId, String apiName, String status, String apiDescription,
			ApiApplication apiApplication, String apiSwagUrl, Date createdDate, Date updatedDate,
			ApiCatagory apiCatagory, UserProfile userProfile, List<ApiModel> apiModels) {
		super();
		this.apiId = apiId;
		this.apiName = apiName;
		this.status = status;
		this.apiDescription = apiDescription;
		this.apiApplication = apiApplication;
		this.apiSwagUrl = apiSwagUrl;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.apiCatagory = apiCatagory;
		this.userProfile = userProfile;
		this.apiModels = apiModels;
	}



	public Long getApiId() {
		return apiId;
	}



	public void setApiId(Long apiId) {
		this.apiId = apiId;
	}



	public String getApiName() {
		return apiName;
	}



	public void setApiName(String apiName) {
		this.apiName = apiName;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getApiDescription() {
		return apiDescription;
	}



	public void setApiDescription(String apiDescription) {
		this.apiDescription = apiDescription;
	}



	public ApiApplication getApiApplication() {
		return apiApplication;
	}



	public void setApiApplication(ApiApplication apiApplication) {
		this.apiApplication = apiApplication;
	}



	public String getApiSwagUrl() {
		return apiSwagUrl;
	}



	public void setApiSwagUrl(String apiSwagUrl) {
		this.apiSwagUrl = apiSwagUrl;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public Date getUpdatedDate() {
		return updatedDate;
	}



	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}



	public ApiCatagory getApiCatagory() {
		return apiCatagory;
	}



	public void setApiCatagory(ApiCatagory apiCatagory) {
		this.apiCatagory = apiCatagory;
	}



	public UserProfile getUserProfile() {
		return userProfile;
	}



	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}



	public List<ApiModel> getApiModels() {
		return apiModels;
	}



	public void setApiModels(List<ApiModel> apiModels) {
		this.apiModels = apiModels;
	}


     
     
}
