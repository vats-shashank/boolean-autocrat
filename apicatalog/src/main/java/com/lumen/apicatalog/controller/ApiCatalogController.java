package com.lumen.apicatalog.controller;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lumen.apicatalog.DTO.DataDto;
import com.lumen.apicatalog.constants.Constants;
import com.lumen.apicatalog.dao.EmailRequest;
import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.ApiCatalogInfo;
import com.lumen.apicatalog.service.ApicataLogService;

@RestController
/* @CrossOrigin(origins = "*") */
@RequestMapping(value = "/api-v1-catalog", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiCatalogController {

	@Autowired
	private ApicataLogService apicataLogService;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<DataDto> createApi(@RequestBody ApiCatalogInfo apiCatalog) {
		try {
			apicataLogService.createApi(apiCatalog);
			
		return new  ResponseEntity<>(new DataDto("success","200"),HttpStatus.OK);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e.getHttpStatus());
		}

	}

	@PostMapping("/update")
	public ResponseEntity<DataDto>  updateApi(@RequestBody ApiCatalogInfo apiCatalog) {
		try {
			apicataLogService.updateApi(apiCatalog);
			return new  ResponseEntity<>(new DataDto("success","200"),HttpStatus.OK);
		} catch (BusinessException be) {
			throw new BusinessException(be.getMessage(), be.getHttpStatus());
		}

	}
	
	@PostMapping("/sendMail")
	public void sendMail(@RequestBody EmailRequest req) {
		
		jmsTemplate.convertAndSend(Constants.MESSAGE_DESTINATION_NAME, req);
	}
}
