package com.lumen.apicatalog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.dao.ApiAplicationDao;
import com.lumen.apicatalog.dao.ApiCatagoryDao;
import com.lumen.apicatalog.dao.ApiCatalogDao;
import com.lumen.apicatalog.dao.ApiModelDao;
import com.lumen.apicatalog.dao.UserProfileDao;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiApplication;
import com.lumen.apicatalog.model.ApiCatagory;
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

	@Autowired
	private ApiCatagoryDao apiCatagoryDao;

	private static String MODEL_STATUS = "ACTIVE";

	private static String EMAIL = "Rajeev.K.Singh@lumen.com";

	@Autowired
	private ApiModelDao apiModelDao;

	public void updateApi(ApiCatalogInfo apiCatalog) {

		ApiCatalogInfo apCatalog = apiCatalogDao.findById(apiCatalog.getApiId()).orElse(null);
		if (null != apCatalog) {

			ApiCatagory apiCatagory = apiCatagoryDao
					.findByApiCatagoryName(apiCatalog.getApiCatagory().getApiCatagoryName());
			if (null == apiCatagory) {
				apiCatagory = this.createApiCatagory(apiCatalog.getApiCatagory());
				System.out.println("inside null" + apiCatagory.getApiCatagoryName());
				apCatalog.setApiCatagory(apiCatalog.getApiCatagory());
			}
			
			if(!apCatalog.getApiName().equals(apiCatalog.getApiName()) &&null!=apiCatalogDao.getByApiName(apiCatalog.getApiName()))
			throw new BusinessException("API name already taken", HttpStatus.CONFLICT);
		
			apCatalog.setApiName(apiCatalog.getApiName());
			apCatalog.setApiSwagUrl(apiCatalog.getApiSwagUrl());
			apCatalog.setApiDescription(apiCatalog.getApiDescription());
			apiCatalogDao.save(apCatalog);
			this.updateModel(apiCatalog.getApiModels(), apiCatalog);

			List<Long> existIds = new ArrayList<>();

			apiCatalog.getApiModels().stream().filter(a -> a.getModelId() > 0).collect(Collectors.toList()).stream()
					.forEach(b -> existIds.add(b.getModelId()));	
	    	List<Long> allIds= apCatalog.getApiModels().stream()
                .map(ApiModel::getModelId).collect(Collectors.toList());
			List<Long> remaining=new ArrayList<>(allIds);
			remaining.removeAll(existIds);
			remaining.stream().forEach(a->{
				apiModelDao.delete(apiModelDao.getById(a));
			});

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

			ApiCatagory apiCatagory = apiCatagoryDao
					.findByApiCatagoryName(apiCatalog.getApiCatagory().getApiCatagoryName());
			if (null == apiCatagory)
				apiCatagory = this.createApiCatagory(apiCatalog.getApiCatagory());
			UserProfile userProfile = this.getUserInfo(apiCatalog.getUserProfile());
			apiCatalog.setUserProfile(userProfile);
			apiCatalog.setApiCatagory(apiCatagory);
			apiCatalog.setStatus(MODEL_STATUS);
			apiCatalog.setApiApplication(apiApplication);
			apiCatalog = apiCatalogDao.save(apiCatalog);
		} else {
			throw new BusinessException("API name already taken", HttpStatus.CONFLICT);
		}
		this.createApiModels(apiCatalog.getApiModels(), apiCatalog);
	}

	private ApiCatagory createApiCatagory(ApiCatagory apiCatagory) {

		if (null == apiCatagory.getApiCatagoryName() || apiCatagory.getApiCatagoryName().isEmpty())
			throw new BusinessException("Catagory name can not be empty", HttpStatus.BAD_REQUEST);
		apiCatagory = apiCatagoryDao.saveAndFlush(apiCatagory);
		return apiCatagory;
	}

	private UserProfile getUserInfo(UserProfile userProfile) {
		UserProfile usProfile = userProfileDao.findByUserCuid(userProfile.getUserCuid());
		if (null == usProfile) {
			userProfile.setUserCuid(userProfile.getUserCuid());
			userProfile.setEmailAddress(EMAIL);
			userProfile.setFirstName("XX" + userProfile.getUserCuid() + "Ab");
			userProfile.setLastName("xxx" + "Last Name");
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

	private void updateModel(List<ApiModel> apiModels, ApiCatalogInfo apiCatalog) {
		for (ApiModel apiModel : apiModels) {

			if (null == apiModel.getModelId() || apiModel.getModelId() == 0) {
				this.createApModels(apiModel, apiCatalog);
			}

			else {
				ApiModel apModel = apiModelDao.findById(apiModel.getModelId()).get();
				if (null != apModel) {
					apModel.setModelName(apiModel.getModelName());
					apModel.setModelType(apModel.getModelType());
					apModel.setApiCatalogInfo(apiCatalog);
					apiModelDao.save(apModel);
				} else {
					throw new BusinessException("Api model id is wrong", HttpStatus.BAD_REQUEST);
				}
			}

		}
	}

	private void createApModels(ApiModel apModel, ApiCatalogInfo apiCatalog) {
		apModel.setApiCatalogInfo(apiCatalog);
		apModel.setStatus(MODEL_STATUS);
		apiModelDao.save(apModel);
	}

	private ApiApplication creeateApiApplication(String applicationName) {
		ApiApplication apiApplication = new ApiApplication();
		apiApplication.setAppName(applicationName);
		apiAplicationDao.save(apiApplication);
		return apiApplication;
	}

}
