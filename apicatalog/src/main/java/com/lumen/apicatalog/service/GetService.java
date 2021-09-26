package com.lumen.apicatalog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lumen.apicatalog.DTO.ResponseDTO;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiApplication;
import com.lumen.apicatalog.model.ApiCatagory;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.model.ApiModel;
import com.lumen.apicatalog.model.UserProfile;
import com.lumen.apicatalog.repository.ApiApplicationRepository;
import com.lumen.apicatalog.repository.ApiCatagoryRepository;
import com.lumen.apicatalog.repository.ApiCatalogInfoRepository;
import com.lumen.apicatalog.repository.ApiModelRepository;
import com.lumen.apicatalog.repository.UserProfileRepository;
import com.lumen.apicatalog.util.MiscUtility;

@Service
public class GetService {
	private static final Logger logger = LoggerFactory.getLogger(GetService.class);

	@Autowired
	ApiCatagoryRepository apiCatagoryRepository;
	@Autowired
	ApiCatalogInfoRepository apiCatalogInfoRepository;
	@Autowired
	ApiApplicationRepository apiApplicationRepository;
	@Autowired
	ApiModelRepository apiModelRepository;
	@Autowired
	UserProfileRepository userProfileRepository;

	@Autowired
	MiscUtility miscUtility;

	public List<ResponseDTO> getAPIByCategory(String categoryName) {
		logger.info("getAPIByCategory start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		List<ApiCatalogInfo> apiCatalogInfos = new ArrayList<ApiCatalogInfo>();
		try {
			List<ApiCatagory> apiCatagorys = apiCatagoryRepository.getByCategoryName(categoryName);
			if(apiCatagorys!=null && apiCatagorys.size()>0) {
				for (ApiCatagory apiCatagory : apiCatagorys) {
					apiCatalogInfos.addAll(apiCatalogInfoRepository.getByCategoryId(String.valueOf(apiCatagory.getApiCatagoryId())));
				}
			}
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfos);
		} catch (Exception e) {
			logger.error("Exception in getAPIByCategory : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("getAPIByCategory end");
		return responseDTOs;
	}

	public List<ResponseDTO> getAPIByAPIName(String apiName) {
		logger.info("getAPIByAPIName start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			List<ApiCatalogInfo> apiCatalogInfo = apiCatalogInfoRepository.getByApiName(apiName);
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfo);
		} catch (Exception e) {
			logger.error("Exception in getAPIByAPIName : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("getAPIByAPIName end");
		return responseDTOs;
	}

	public List<ResponseDTO> getAPIByAPIDesc(String apiDesc) {
		logger.info("getAPIByAPIDesc start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			List<ApiCatalogInfo> apiCatalogInfo = apiCatalogInfoRepository.getByApiDec(apiDesc);
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfo);
		} catch (Exception e) {
			logger.error("Exception in getAPIByAPIDesc : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("getAPIByAPIDesc end");
		return responseDTOs;
	}
	
	public List<ResponseDTO> getAPIByAppName(String apiAppName) {
		logger.info("getAPIByAppName start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		List<ApiCatalogInfo> apiCatalogInfos = new ArrayList<ApiCatalogInfo>();
		try {
			List<ApiApplication> apiApplications = apiApplicationRepository.getByAppName(apiAppName);
			if(apiApplications!=null && apiApplications.size()>0) {
				for (ApiApplication apiApplication : apiApplications) {
					apiCatalogInfos.addAll(apiCatalogInfoRepository.getByAppID(String.valueOf(apiApplication.getAppId())));
				}
			}
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfos);
		} catch (Exception e) {
			logger.error("Exception in getAPIByAppName : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("getAPIByAppName end");
		return responseDTOs;
	}


	public List<ResponseDTO> getAPIByModelName(String modelName) {
		logger.info("getAPIByModelName start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		List<ApiModel> apiModels = new ArrayList<ApiModel>();
		List<ApiCatalogInfo> apiCatalogInfos = new ArrayList<ApiCatalogInfo>();
		try {
			apiModels = apiModelRepository.getByModelName(modelName);
			List<ApiModel> apiModelsFiltered = apiModels.stream() 
					  .filter(distinctByKey(obj -> obj.getApiCatalogInfo().getApiId())) 
					  .collect(Collectors.toList());
			
			apiModelsFiltered.stream().forEach(apiModel->{
				apiCatalogInfos.add(apiModel.getApiCatalogInfo());
			});
			
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfos);
		} catch (Exception e) {
			logger.error("Exception in getAPIByModelName : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("getAPIByModelName end");
		return responseDTOs;
	}
	
	public static <T> Predicate<T> distinctByKey(
		    Function<? super T, ?> keyExtractor) {
		  
		    Map<Object, Boolean> seen = new ConcurrentHashMap<>(); 
		    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; 
		}


	public List<ResponseDTO> getAPIByModelNameType(String modelName,String modelType) {
		logger.info("getAPIByModelNameType start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		List<ApiModel> apiModels = new ArrayList<ApiModel>();
		List<ApiCatalogInfo> apiCatalogInfos = new ArrayList<ApiCatalogInfo>();
		try {
			apiModels = apiModelRepository.getByModelName_ModelType(modelName, modelType);
			List<ApiModel> apiModelsFiltered = apiModels.stream() 
					  .filter(distinctByKey(obj -> obj.getApiCatalogInfo().getApiId())) 
					  .collect(Collectors.toList());
			
			apiModelsFiltered.stream().forEach(apiModel->{
				apiCatalogInfos.add(apiModel.getApiCatalogInfo());
			});
			
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfos);
		} catch (Exception e) {
			logger.error("Exception in getAPIByModelNameType : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("getAPIByModelNameType end");
		return responseDTOs;
	}

	public List<ResponseDTO> searchAPI(String text) {
		logger.info("getAPIByCategory start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			List<ApiCatalogInfo> apiCatalogInfo = apiCatalogInfoRepository.findAll();
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfo);
		} catch (Exception e) {
			logger.error("Exception in getAPIByCategory : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
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

	public List<ResponseDTO> getAPIByUserCuid(String cuid) {
		logger.info("getAPIByUserCuid start");
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			UserProfile userProfile = userProfileRepository.getByCUID(cuid);
			responseDTOs = miscUtility.getResponseDTO(apiCatalogInfoRepository.getAPIByUserCuid(String.valueOf(userProfile.getUserId())));
		} catch (Exception e) {
			logger.error("Exception in getAPIByUserCuid : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("getAPIByUserCuid end");
		return responseDTOs;
	}

	public List<ApiCatagory> getAllCategories() {
		logger.info("getAPIByUserCuid start");
		List<ApiCatagory> apiCatagories = new ArrayList<ApiCatagory>();
		try {
			apiCatagories = apiCatagoryRepository.findAll();
		} catch (Exception e) {
			logger.error("Exception in getAPIByUserCuid : ", e.getMessage());
			throw new BusinessException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		logger.info("getAPIByUserCuid end");
		return apiCatagories;
	}
	
	
}
