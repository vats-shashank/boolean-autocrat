package com.lumen.apicatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.model.ApiModel;

@Repository
public interface ApiModelRepository extends JpaRepository<ApiModel, Long>{
	@Transactional
	@Query(value = "select * from TJAM_API_MODEL where MODEL_NAME like %?1%", nativeQuery = true)
	List<ApiModel> getByModelName(String modelName);
	
	@Transactional
	List<ApiModel> findByModelNameContainingIgnoreCase(String modelName);
	
	@Transactional
	@Query(value = "select * from TJAM_API_MODEL where MODEL_NAME like %?1% and MODEL_TYPE=?2", nativeQuery = true)
	List<ApiModel> getByModelName_ModelType(String modelName,String modelType);
	
	
	@Transactional
	@Query(value = "and MODEL_TYPE=?2", nativeQuery = true)
	List<ApiModel> findByModelNameContainingIgnoreCase(String modelName,String modelType);
}
