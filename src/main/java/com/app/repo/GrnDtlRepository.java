package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.model.GrnDtl;

public interface GrnDtlRepository extends JpaRepository<GrnDtl, Integer>{

	@Query("SELECT dtl FROM GrnDtl dtl INNER JOIN dtl.grn AS grn WHERE grn.id=:grnId")
	public List<GrnDtl>getAllBtlsByGrnId(Integer grnId);

	//@Query + @Modifying ==> non- select
	@Modifying
	@Query("UPDATE GrnDtl set status=:status WHERE id=:id")
	void updateStatusByGrnDtlId(String status,Integer id);


}
