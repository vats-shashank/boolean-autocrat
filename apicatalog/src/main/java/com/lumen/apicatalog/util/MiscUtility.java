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
		if (apiCatalogInfos != null && apiCatalogInfos.size() > 0) {
			for (ApiCatalogInfo apiCatalogInfo : apiCatalogInfos) {
				responseDTO = new ResponseDTO();

				responseDTO.setApiName(apiCatalogInfo.getApiName());
				responseDTO.setApiDescription(apiCatalogInfo.getApiDescription());
				responseDTO.setApiSwagUrl(apiCatalogInfo.getApiSwagUrl());
				responseDTO.setStatus(apiCatalogInfo.getStatus());
				responseDTO.setApiId(String.valueOf(apiCatalogInfo.getApiId()));

				responseDTO.setAppId(String.valueOf(apiCatalogInfo.getApiApplication().getAppId()));
				responseDTO.setAppName(apiCatalogInfo.getApiApplication().getAppName());

				responseDTO.setUserId(String.valueOf(apiCatalogInfo.getUserProfile().getUserId()));
				responseDTO.setUserCuid(apiCatalogInfo.getUserProfile().getUserCuid());

				responseDTO.setEmailAddress(apiCatalogInfo.getUserProfile().getEmailAddress());
				responseDTO.setApiCatagoryId(String.valueOf(apiCatalogInfo.getApiCatagory().getApiCatagoryId()));
				responseDTO.setApiCatagoryName(apiCatalogInfo.getApiCatagory().getApiCatagoryName());

				List<ApiModel> apiModels = apiCatalogInfo.getApiModels();
				ApiModelDTO apiModelDTO = null;
				List<ApiModelDTO> apiModelDTOs = new ArrayList<ApiModelDTO>();
				if (apiModels != null && apiModels.size() > 0) {
					for (ApiModel apiModel : apiModels) {
						apiModelDTO = new ApiModelDTO();
						apiModelDTO.setModelId(String.valueOf(apiModel.getModelId()));
						apiModelDTO.setModelName(apiModel.getModelName());
						apiModelDTO.setStatus(apiModel.getStatus());
						apiModelDTO.setModelType(apiModel.getModelType());
						apiModelDTO.setApiId(String.valueOf(apiModel.getApiCatalogInfo().getApiId()));
						apiModelDTOs.add(apiModelDTO);
					}
					responseDTO.setApiModels(apiModelDTOs);
				}

				responseDTOs.add(responseDTO);
			}
		}
		return responseDTOs;
	}

	public List<ApiModelDTO> getApiModelDTO(List<ApiModel> apiModels) {
		ApiModelDTO apiModelDTO = null;
		List<ApiModelDTO> apiModelDTOs = new ArrayList<ApiModelDTO>();
		if (apiModels != null && apiModels.size() > 0) {
				for (ApiModel apiModel : apiModels) {
					apiModelDTO = new ApiModelDTO();
					apiModelDTO = new ApiModelDTO();
					apiModelDTO.setModelId(String.valueOf(apiModel.getModelId()));
					apiModelDTO.setModelName(apiModel.getModelName());
					apiModelDTO.setStatus(apiModel.getStatus());
					apiModelDTO.setModelType(apiModel.getModelType());
					apiModelDTO.setApiId(String.valueOf(apiModel.getApiCatalogInfo().getApiId()));
					apiModelDTOs.add(apiModelDTO);
				}
		}
		return apiModelDTOs;
	}
}
