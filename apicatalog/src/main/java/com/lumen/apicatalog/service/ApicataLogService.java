package com.lumen.apicatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lumen.apicatalog.dao.ApiCatalogDao;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiCatalogInfo;

@Service
public class ApicataLogService {

	@Autowired
	private ApiCatalogDao apiCatalogDao;

	public void updateApi(ApiCatalogInfo apiCatalog) {

		ApiCatalogInfo apCatalog = apiCatalogDao.getById(apiCatalog.getApiId());
		if (null != apCatalog) {
			apCatalog.setApiCatagory(apiCatalog.getApiCatagory());
			apCatalog.setApiName(apiCatalog.getApiName());
			apCatalog.setApiSwagUrl(apCatalog.getApiSwagUrl());
			apCatalog.setApiDescription(apiCatalog.getApiDescription());
			apiCatalogDao.save(apCatalog);
		} else {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "API Not Availabe");
		}

	}

	public void createApi(ApiCatalogInfo apiCatalog) {

		ApiCatalogInfo apCatalog = apiCatalogDao.getByApiName(apiCatalog.getApiName());
		if (null == apCatalog) {

			apiCatalogDao.save(apCatalog);
		} else {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "API Name already taken");
		}

	}

}
