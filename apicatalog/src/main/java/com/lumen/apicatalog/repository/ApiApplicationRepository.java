package com.lumen.apicatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.model.ApiApplication;

@Repository
public interface ApiApplicationRepository extends JpaRepository<ApiApplication, Long> {
	@Transactional
	@Query(value = "select * from TJAM_API_APPLICATION where TJAM_APP_NAME like %?1%", nativeQuery = true)
	List<ApiApplication> getByAppName(String appName);
	
	@Transactional
	List<ApiApplication> findByAppNameContainingIgnoreCase(String text);
}
