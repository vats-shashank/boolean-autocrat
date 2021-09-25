package com.lumen.apicatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumen.apicatalog.model.ApiCatalogInfo;

public interface ApiCatalogDao extends JpaRepository<ApiCatalogInfo,Long>{

	ApiCatalogInfo getByApiName(String apiName);

}
