package com.lumen.apicatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumen.apicatalog.model.ApiCatalogInfo;

@Repository
public interface ApiCatalogDao extends JpaRepository<ApiCatalogInfo,Long>{

	ApiCatalogInfo getByApiName(String apiName);
	

}
