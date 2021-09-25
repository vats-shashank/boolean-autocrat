package com.lumen.apicatalog.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lumen.apicatalog.DTO.ResponseDTO;
import com.lumen.apicatalog.model.ApiCatagory;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.repository.ApiCatagoryRepository;
import com.lumen.apicatalog.repository.ApiCatalogInfoRepository;

@Service
public class GetService {
	private static final Logger logger = LoggerFactory.getLogger(GetService.class);
	
	@Autowired
	ApiCatagoryRepository apiCatagoryRepository;
	
	@Autowired
	ApiCatalogInfoRepository apiCatalogInfoRepository;

	public List<ResponseDTO> getAPIByCategory(String categoryName) {
		logger.info("getAPIByCategory start");
		
		ApiCatagory apiCatagory =  apiCatagoryRepository.getByCategoryName(categoryName);
		logger.info(""+apiCatagory.getApiCatagoryId());
		getApiCatalogInfo(String.valueOf(apiCatagory.getApiCatagoryId()));
		
		logger.info("getAPIByCategory end");
		return null;
	}

	public List<ResponseDTO> getAPIByAPIName(String apiName) {
		return null;
	}

	public List<ResponseDTO> getAPIByAPIDesc(String apiDesc) {
		return null;
	}

	public List<ResponseDTO> getAPIByModel(String apiModel) {
		return null;
	}

	public List<ResponseDTO> getAPIByAppName(String apiAppName) {
		return null;
	}
	
	public List<ResponseDTO> searchAPI(String text) {
		return null;
	}
	
	
	public void getApiCatalogInfo(String id) {
		logger.info("getApiCatalogInfo start");
		List<ApiCatalogInfo> apiCatalogInfo = apiCatalogInfoRepository.getByCategoryId(id);
		apiCatalogInfo.forEach(System.out::println);
		logger.info("getApiCatalogInfo end");
		
	}
}
