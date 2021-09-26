package com.lumen.apicatalog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lumen.apicatalog.dao.ApiCatalogDao;
import com.lumen.apicatalog.model.ApiCatalogInfo;
@Service
public class CatalogInfoSvc {
	
	@Autowired
	private ApiCatalogDao apiCatalogDao;
	
	public List<ApiCatalogInfo> getAPIInfo(){
		List<ApiCatalogInfo> apiInfo = new ArrayList<ApiCatalogInfo>();
		apiInfo = apiCatalogDao.findAll();
		
		return apiInfo;
		
	}
}
