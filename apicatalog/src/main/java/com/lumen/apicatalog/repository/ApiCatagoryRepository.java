package com.lumen.apicatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.model.ApiCatagory;

@Repository
public interface ApiCatagoryRepository extends JpaRepository<ApiCatagory, Long> {
	@Transactional    
	@Query(value="select * from TJAM_API_CATEGORY where CATEGORY_NAME=?1", nativeQuery = true)
		ApiCatagory getByCategoryName(String categoryName);

}
