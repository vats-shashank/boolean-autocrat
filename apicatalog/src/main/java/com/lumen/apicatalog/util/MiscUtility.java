package com.lumen.apicatalog.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lumen.apicatalog.DTO.ApiModelDTO;
import com.lumen.apicatalog.DTO.ResponseDTO;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.model.ApiModel;

@Component
public class MiscUtility {
	public List<ResponseDTO> getResponseDTO(List<ApiCatalogInfo> apiCatalogInfos) {
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		ResponseDTO responseDTO = null;
		
		for (ApiCatalogInfo apiCatalogInfo : apiCatalogInfos) {
			responseDTO = new ResponseDTO();
			
			responseDTO.setAPI_NAME(apiCatalogInfo.getApiName());
			responseDTO.setAPI_DESC(apiCatalogInfo.getApiDescription());
			responseDTO.setSWAGGER_URL(apiCatalogInfo.getApiSwagUrl());
			responseDTO.setAPI_STATUS(apiCatalogInfo.getStatus());
			responseDTO.setAPI_ID(String.valueOf(apiCatalogInfo.getApiId()));
			
			responseDTO.setAPP_ID(String.valueOf(apiCatalogInfo.getApiApplication().getAppId()));
			responseDTO.setAPP_NAME(apiCatalogInfo.getApiApplication().getAppName());
			
			
			responseDTO.setUSER_ID(String.valueOf(apiCatalogInfo.getUserProfile().getUserId()));
			responseDTO.setCUID(apiCatalogInfo.getUserProfile().getUserCuid());
			
			responseDTO.setEMAIL_ADDRESS(apiCatalogInfo.getUserProfile().getEmailAddress());
			responseDTO.setCATEGORY_ID(String.valueOf(apiCatalogInfo.getApiCatagory().getApiCatagoryId()));
			responseDTO.setCATEGORY_NAME(apiCatalogInfo.getApiCatagory().getApiCatagoryName());
			
			List<ApiModel> apiModels = apiCatalogInfo.getApiModels();
			ApiModelDTO apiModelDTO = null;
			List<ApiModelDTO> apiModelDTOs = new ArrayList<ApiModelDTO>();
			if(apiModels!=null && apiModels.size()>0) {
				for (ApiModel apiModel : apiModels) {
					apiModelDTO = new ApiModelDTO();
					apiModelDTO.setMODEL_ID(String.valueOf(apiModel.getModelId()));
					apiModelDTO.setMODEL_NAME(apiModel.getModelName());
					apiModelDTO.setMODEL_STATUS(apiModel.getStatus());
					apiModelDTO.setMODEL_TYPE(apiModel.getModelType());
					apiModelDTOs.add(apiModelDTO);
				}
				responseDTO.setAPI_MODEL(apiModelDTOs);
			}
			
			responseDTOs.add(responseDTO);
		}
		return responseDTOs;
	}
}
