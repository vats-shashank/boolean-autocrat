package com.lumen.apicatalog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lumen.apicatalog.DTO.ResponseDTO;
import com.lumen.apicatalog.model.UserProfile;
import com.lumen.apicatalog.service.GetService;
import com.lumen.apicatalog.service.LdapService;

/**
 * 
 * @author Pooja
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api-v1-get")
public class GetAPIController {

	@Autowired
	private GetService getService;

	@GetMapping("/category/{category}")
	public ResponseEntity<List<ResponseDTO>> getAPIByCategory(@PathVariable String category) {
		ResponseEntity<List<ResponseDTO>> responseEntity = null;
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			responseDTOs.addAll(getService.getAPIByCategory(category));
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.OK);
		} catch (Exception e) {
			responseDTOs = null;
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	@GetMapping("/apiName/{apiName}")
	public ResponseEntity<List<ResponseDTO>> getAPIByAPIName(@PathVariable String apiName) {
		ResponseEntity<List<ResponseDTO>> responseEntity = null;
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			responseDTOs.addAll(getService.getAPIByAPIName(apiName));
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.OK);
		} catch (Exception e) {
			responseDTOs = null;
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}

	@GetMapping("/apiDesc/{apiDesc}")
	public ResponseEntity<List<ResponseDTO>> getAPIByAPIDesc(@PathVariable String apiDesc) {
		ResponseEntity<List<ResponseDTO>> responseEntity = null;
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			responseDTOs.addAll(getService.getAPIByAPIDesc(apiDesc));
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.OK);
		} catch (Exception e) {
			responseDTOs = null;
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	@GetMapping("/apiAppName/{apiAppName}")
	public ResponseEntity<List<ResponseDTO>> getAPIByAppName(@PathVariable String apiAppName) {
		ResponseEntity<List<ResponseDTO>> responseEntity = null;
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();

		try {
			responseDTOs.addAll(getService.getAPIByAppName(apiAppName));
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.OK);
		} catch (Exception e) {
			responseDTOs = null;
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.BAD_REQUEST);
		}

		responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/apiModel/{apiModel}")
	public ResponseEntity<List<ResponseDTO>> getAPIByModel(@PathVariable String apiModel) {
		ResponseEntity<List<ResponseDTO>> responseEntity = null;
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			responseDTOs.addAll(getService.getAPIByModel(apiModel));
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.OK);
		} catch (Exception e) {
			responseDTOs = null;
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	@GetMapping("/search/{text}")
	public ResponseEntity<List<ResponseDTO>> searchAPI(@PathVariable String text) {
		ResponseEntity<List<ResponseDTO>> responseEntity = null;
		List<ResponseDTO> responseDTOs = new ArrayList<ResponseDTO>();
		try {
			responseDTOs.addAll(getService.searchAPI(text));
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.OK);
		} catch (Exception e) {
			responseDTOs = null;
			responseEntity = new ResponseEntity<List<ResponseDTO>>(responseDTOs, HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	@Autowired
	private LdapService ldapService;

	@GetMapping("/getUser/{cuid}")
	public ResponseEntity<UserProfile> getUser(@PathVariable String cuid) {
		ResponseEntity<UserProfile> responseEntity = new ResponseEntity<UserProfile>(
				ldapService.getSetUserProfile(cuid), HttpStatus.OK);
		return responseEntity;
	}

}
