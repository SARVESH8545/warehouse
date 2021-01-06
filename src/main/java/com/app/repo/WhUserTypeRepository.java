package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.model.WhUserType;

public interface WhUserTypeRepository extends JpaRepository<WhUserType, Integer> {
	@Query("SELECT COUNT(WH.userMail) FROM WhUserType WH WHERE WH.userMail=:mail")
	public Integer getWhUserTypeCount(String mail);
	
	@Query("SELECT WT.userType, count(WT.userType) FROM WhUserType  WT GROUP BY WT.userType")
	public List<Object[]> getWhUserTypeCount();
	
	@Query("SELECT WH.id, WH.userCode FROM WhUserType WH WHERE WH.userType=:userType")
	public List<Object[]> getWhUserTypeIdAndCode(String userType);
	
}
