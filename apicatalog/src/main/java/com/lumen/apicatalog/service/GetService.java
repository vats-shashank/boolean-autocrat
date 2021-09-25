package com.lumen.apicatalog.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lumen.apicatalog.DTO.ResponseDTO;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiCatagory;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.repository.ApiCatagoryRepository;
import com.lumen.apicatalog.repository.ApiCatalogInfoRepository;
import com.lumen.apicatalog.util.MiscUtility;

@Service
public class GetService {
	private static final Logger logger = LoggerFactory.getLogger(GetService.class);

	@Autowired
	ApiCatagoryRepository apiCatagoryRepository;

	@Autowired
	ApiCatalogInfoRepository apiCatalogInfoRepository;

	@Autowired
	MiscUtility miscUtility;

	public List<ResponseDTO> getAPIByCategory(String categoryName) {
		logger.info("getAPIByCategory start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			ApiCatagory apiCatagory = apiCatagoryRepository.getByCategoryName(categoryName);
			List<ApiCatalogInfo> apiCatalogInfo = apiCatalogInfoRepository.getByCategoryId(String.valueOf(apiCatagory.getApiCatagoryId()));
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfo);

		} catch (Exception e) {
			logger.error("Exception in getAPIByCategory : ", e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		logger.info("getAPIByCategory end");
		return responseDTOs;
	}

	public List<ResponseDTO> getAPIByAPIName(String apiName) {
		logger.info("getAPIByCategory start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			
		} catch (Exception e) {
			logger.error("Exception in getAPIByCategory : ", e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		logger.info("getAPIByCategory end");
		return responseDTOs;
	}

	public List<ResponseDTO> getAPIByAPIDesc(String apiDesc) {
		logger.info("getAPIByCategory start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			
		} catch (Exception e) {
			logger.error("Exception in getAPIByCategory : ", e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		logger.info("getAPIByCategory end");
		return responseDTOs;
	}

	public List<ResponseDTO> getAPIByModel(String apiModel) {
		logger.info("getAPIByCategory start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			
		} catch (Exception e) {
			logger.error("Exception in getAPIByCategory : ", e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		logger.info("getAPIByCategory end");
		return responseDTOs;
	}

	public List<ResponseDTO> getAPIByAppName(String apiAppName) {
		logger.info("getAPIByCategory start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			
		} catch (Exception e) {
			logger.error("Exception in getAPIByCategory : ", e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		logger.info("getAPIByCategory end");
		return responseDTOs;
	}

	public List<ResponseDTO> searchAPI(String text) {
		logger.info("getAPIByCategory start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			
		} catch (Exception e) {
			logger.error("Exception in getAPIByCategory : ", e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		logger.info("getAPIByCategory end");
		return responseDTOs;
	}

	public void getApiCatalogInfo(String id) {
		logger.info("getApiCatalogInfo start");
		List<ApiCatalogInfo> apiCatalogInfo = apiCatalogInfoRepository.getByCategoryId(id);
		apiCatalogInfo.forEach(System.out::println);
		logger.info("getApiCatalogInfo end");

	}
}
