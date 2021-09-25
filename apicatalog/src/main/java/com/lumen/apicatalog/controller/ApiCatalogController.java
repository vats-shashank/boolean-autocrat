package com.lumen.apicatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lumen.apicatalog.dao.ApiCatalogDao;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.service.ApicataLogService;

@RestController
@RequestMapping("/api-v1-catalog")
public class ApiCatalogController {
	
	@Autowired
	private ApiCatalogDao apiCatalogDao;
	
	@Autowired
	private ApicataLogService apicataLogService;
	
	@PostMapping("/create")
	public void createApi(@RequestBody ApiCatalogInfo apiCatalog) {
		try {

		 apiCatalogDao.save(apiCatalog);
			
		} catch (BusinessException e) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "", e);
		}

	}

	@PostMapping("/update")
	public void updateApi(@RequestBody ApiCatalogInfo apiCatalog) {
		try {
	
		apicataLogService.updateApi(apiCatalog);
			
		} catch (BusinessException e) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "", e);
		}

	}
	
}
