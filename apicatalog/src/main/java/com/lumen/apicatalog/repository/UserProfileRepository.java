package com.lumen.apicatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumen.apicatalog.model.UserProfile;

/**
 * 
 * @author Pooja
 *
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

	@Transactional    
	@Query(value="select * from TJAM_API_USER_PROFILE where USER_CUID=?1", nativeQuery = true)
		UserProfile getByCUID(String cuid);
	
}
