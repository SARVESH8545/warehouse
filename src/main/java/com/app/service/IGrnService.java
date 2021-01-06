package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.Grn;
import com.app.model.GrnDtl;

public interface IGrnService {
	Integer saveGrn(Grn grn);
	void updateGrn(Grn grn);
	
	void deleteGrn(Integer id);
	Optional<Grn> getOneGrn(Integer id);
	
	List<Grn> getAllGrns();
	boolean isGrnExist(Integer id);
	
	//pagination
	Page<Grn> getAllGrn(Pageable pageable);
	
    //------Screen#2-----//
	Integer saveGrnDtl(GrnDtl dtl);
	List<GrnDtl> getAllDtlsByGrnId(Integer grnId);
	void updateStatusByGrnDtlId(String status,Integer id);
}
