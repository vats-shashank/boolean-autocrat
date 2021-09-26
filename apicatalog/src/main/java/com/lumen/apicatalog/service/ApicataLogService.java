package com.lumen.apicatalog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.dao.ApiAplicationDao;
import com.lumen.apicatalog.dao.ApiCatalogDao;
import com.lumen.apicatalog.dao.ApiModelDao;
import com.lumen.apicatalog.dao.UserProfileDao;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiApplication;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.model.ApiModel;
import com.lumen.apicatalog.model.UserProfile;

@Service
@Transactional
public class ApicataLogService {

	@Autowired
	private ApiCatalogDao apiCatalogDao;

	@Autowired
	private ApiAplicationDao apiAplicationDao;
	
	@Autowired
	private UserProfileDao userProfileDao;

	private static String MODEL_STATUS = "ACTIVE";
	
	private static String EMAIL="Rajeev.K.Singh@lumen.com";

	@Autowired
	private ApiModelDao apiModelDao;

	public void updateApi(ApiCatalogInfo apiCatalog) {

		ApiCatalogInfo apCatalog = apiCatalogDao.findById(apiCatalog.getApiId()).orElse(null);
		if (null != apCatalog) {
			apCatalog.setApiCatagory(apiCatalog.getApiCatagory());
			apCatalog.setApiName(apiCatalog.getApiName());
			apCatalog.setApiSwagUrl(apiCatalog.getApiSwagUrl());
			apCatalog.setApiDescription(apiCatalog.getApiDescription());
			apiCatalogDao.save(apCatalog);
		} else {
			throw new BusinessException("Api not found", HttpStatus.NOT_FOUND);
		}

	}
	public void createApi(ApiCatalogInfo apiCatalog) {
		ApiCatalogInfo apCatalog = apiCatalogDao.getByApiName(apiCatalog.getApiName());
		if (null == apCatalog) {
			ApiApplication apiApplication = apiAplicationDao.findByAppName(apiCatalog.getApiApplication().getAppName());
			if (null == apiApplication)
			apiApplication = this.creeateApiApplication(apiCatalog.getApiApplication().getAppName());
		    UserProfile userProfile=this.getUserInfo(apiCatalog.getUserProfile());
			apiCatalog.setUserProfile(userProfile);	
			apiCatalog.setStatus(MODEL_STATUS);
			apiCatalog.setApiApplication(apiApplication);
			apiCatalog = apiCatalogDao.save(apiCatalog);
		} else {
			throw new BusinessException("API name already taken", HttpStatus.CONFLICT);
		}
		this.createApiModels(apiCatalog.getApiModels(), apiCatalog);
	}

	private UserProfile getUserInfo(UserProfile userProfile) {
		
		UserProfile usProfile=userProfileDao.findByUserCuid(userProfile.getUserCuid());
		if (null == usProfile) {
			userProfile.setUserCuid(userProfile.getUserCuid());
			userProfile.setEmailAddress(EMAIL);
			userProfile.setFirstName("XX"+userProfile.getUserCuid()+"Ab");
			userProfile.setLastName("xxx"+"Last Name");
			userProfile.setSupervisorId("xxx" + (userProfile.getUserCuid()));
			userProfileDao.saveAndFlush(userProfile);
			return userProfile;
		}
		
		return usProfile;
	}

	private void createApiModels(List<ApiModel> apiModels, ApiCatalogInfo apiCatalog) {
		for (ApiModel apiModel : apiModels) {
			apiModel.setStatus(MODEL_STATUS);
			apiModel.setApiCatalogInfo(apiCatalog);
			apiModelDao.save(apiModel);
		}
	}

	private ApiApplication creeateApiApplication(String applicationName) {
		ApiApplication apiApplication = new ApiApplication();
		apiApplication.setAppName(applicationName);
		apiAplicationDao.save(apiApplication);
		return apiApplication;
	}

}
