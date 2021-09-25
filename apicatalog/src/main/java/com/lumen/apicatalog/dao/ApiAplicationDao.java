package com.lumen.apicatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumen.apicatalog.model.ApiApplication;

@Repository
public interface ApiAplicationDao extends JpaRepository<ApiApplication,Long> {
	
	ApiApplication findByAppName(String apiName);

}
