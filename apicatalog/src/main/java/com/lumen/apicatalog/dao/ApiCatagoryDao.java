package com.lumen.apicatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumen.apicatalog.model.ApiCatagory;

@Repository
public interface ApiCatagoryDao extends JpaRepository<ApiCatagory, Long>{

	ApiCatagory findByApiCatagoryName(String apiCatagoryName);

}
