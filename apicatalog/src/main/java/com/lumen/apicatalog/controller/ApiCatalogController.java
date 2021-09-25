package com.lumen.apicatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.service.ApicataLogService;

@RestController
@RequestMapping(value = "/api-v1-catalog", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiCatalogController {
	
	
	@Autowired
	private ApicataLogService apicataLogService;
	
	@PostMapping("/create")
	@ResponseBody
	public void createApi(@RequestBody ApiCatalogInfo apiCatalog) {
		try {

			apicataLogService.createApi(apiCatalog);
			
		} catch (BusinessException e) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "error", e);
		}

	}

	@PostMapping("/update")
	public void updateApi(@RequestBody ApiCatalogInfo apiCatalog) {
		try {
	
		apicataLogService.updateApi(apiCatalog);
			
		} catch (BusinessException e) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "error", e);
		}

	}
	
}
