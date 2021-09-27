package com.lumen.apicatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.model.ApiCatagory;

@Repository
public interface ApiCatagoryRepository extends JpaRepository<ApiCatagory, Long> {
	@Transactional    
	@Query(value="select * from TJAM_API_CATEGORY where CATEGORY_NAME like %?1%", nativeQuery = true)
		List<ApiCatagory> getByCategoryName(String categoryName);
	
	@Transactional    
		List<ApiCatagory> findByApiCatagoryNameContainingIgnoreCase(String categoryName);
	
}
