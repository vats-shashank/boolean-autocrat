package com.lumen.apicatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumen.apicatalog.model.UserProfile;

@Repository
public interface UserProfileDao extends JpaRepository<UserProfile, Long> {

	UserProfile findByUserCuid(String cuid);
}
