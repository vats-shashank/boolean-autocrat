package com.lumen.apicatalog.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lumen.apicatalog.dao.ApiAplicationDao;
import com.lumen.apicatalog.dao.ApiCatalogDao;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiApplication;
import com.lumen.apicatalog.model.ApiCatalogInfo;

@Service
public class ApicataLogService {

	@Autowired
	private ApiCatalogDao apiCatalogDao;
	
	@Autowired
	private ApiAplicationDao apiAplicationDao;

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
			apiCatalog.setCreatedDate(new Date(System.currentTimeMillis()));
			apiCatalog.setUpdatedDate(new Date(System.currentTimeMillis()));
			apiCatalogDao.save(apiCatalog);
		} else {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "API Name already taken");
		}

	}

	private ApiApplication creeateApiApplication(String applicationName) {
		ApiApplication apiApplication=new ApiApplication();
		apiApplication.setAppName(applicationName);
		apiAplicationDao.save(apiApplication);
		return apiApplication;
	}

}
