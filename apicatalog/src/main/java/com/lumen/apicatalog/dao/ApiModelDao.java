package com.lumen.apicatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumen.apicatalog.model.ApiModel;

@Repository
public interface ApiModelDao extends JpaRepository<ApiModel, Long>{

}
