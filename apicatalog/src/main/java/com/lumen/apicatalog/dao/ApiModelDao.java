package com.lumen.apicatalog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.model.ApiModel;

@Repository
@Transactional
public interface ApiModelDao extends JpaRepository<ApiModel, Long>{
	


	void deleteByModelIdIn(List<Long> ids);
	
	

}
