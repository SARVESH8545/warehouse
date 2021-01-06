package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.model.PurchaseDetail;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {
	
	@Query("SELECT PDTL FROM PurchaseDetail PDTL INNER JOIN PDTL.po AS PO WHERE PO.id=:purchaseId")
	public List<PurchaseDetail> getPurchaseDtlWithPoId(Integer purchaseId);
	
	@Query("SELECT count(PDTL.id) FROM PurchaseDetail PDTL INNER JOIN PDTL.po AS PO WHERE PO.id=:purchaseId")
	public Integer getPurchaseDtlCountWithPoId(Integer purchaseId);
}
