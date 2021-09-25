package com.lumen.apicatalog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.dao.ApiAplicationDao;
import com.lumen.apicatalog.dao.ApiCatalogDao;
import com.lumen.apicatalog.dao.ApiModelDao;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiApplication;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.model.ApiModel;

@Service
@Transactional
public class ApicataLogService {

	@Autowired
	private ApiCatalogDao apiCatalogDao;
	
	@Autowired
	private ApiAplicationDao apiAplicationDao;
	
	private static String MODEL_STATUS="ACTIVE";
	
	@Autowired
	private ApiModelDao apiModelDao;

	public void updateApi(ApiCatalogInfo apiCatalog) {

		ApiCatalogInfo apCatalog = apiCatalogDao.findById(apiCatalog.getApiId()).orElse(null);
		if(null!=apCatalog)		
		{
			apCatalog.setApiCatagory(apiCatalog.getApiCatagory());
			apCatalog.setApiName(apiCatalog.getApiName());
			apCatalog.setApiSwagUrl(apiCatalog.getApiSwagUrl());
			apCatalog.setApiDescription(apiCatalog.getApiDescription());
			apiCatalogDao.save(apCatalog);
		} else {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "API Not Availabe");
		}

	}

	public void createApi(ApiCatalogInfo apiCatalog) {

		ApiCatalogInfo apCatalog = apiCatalogDao.getByApiName(apiCatalog.getApiName());
		if (null == apCatalog) {
			
		//user=	userSe.getuser(cuid);
		/* apiCatalog.setUserProfile(user); */
		ApiApplication apiApplication=	apiAplicationDao.findByAppName(apiCatalog.getApiApplication().getAppName());
		
		if(null==apiApplication)
			apiApplication=	this.creeateApiApplication(apiCatalog.getApiApplication().getAppName());
			apiCatalog.setApiApplication(apiApplication);
			apiCatalog=	apiCatalogDao.save(apiCatalog);
		} else {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "API Name already taken");
		}
       this.createApiModels(apiCatalog.getApiModels(),apiCatalog);
	}

	private void createApiModels(List<ApiModel> apiModels,ApiCatalogInfo apiCatalog ) {
		for(ApiModel apiModel:apiModels)
		{
			apiModel.setStatus(MODEL_STATUS);
			apiModel.setApiCatalogInfo(apiCatalog);
			apiModelDao.save(apiModel);	
		}
	}

	private ApiApplication creeateApiApplication(String applicationName) {
		ApiApplication apiApplication=new ApiApplication();
		apiApplication.setAppName(applicationName);
		apiAplicationDao.save(apiApplication);
		return apiApplication;
	}

}
