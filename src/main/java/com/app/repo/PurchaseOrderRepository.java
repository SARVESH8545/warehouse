package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
		
	@Query("SELECT PO.qualityCheck, count(PO.qualityCheck) FROM PurchaseOrder PO GROUP BY PO.qualityCheck")
	public List<Object[]>getQualityCheckCount();
	//for AJAX call
	@Query("SELECT COUNT(PO.orderCode) FROM PurchaseOrder PO WHERE PO.orderCode=:code")
	public Integer getPurchaseOrderCodeCount(String code);
	
	@Modifying
	@Query("UPDATE PurchaseOrder SET status=:status WHERE id=:id")
	public void updatePurchaseOrderStatus(String status,Integer id);
	
	@Query("SELECT PO.id,PO.orderCode FROM PurchaseOrder PO WHERE PO.status=:status")
	public List<Object[]> getPoIdAndCodeByStatus(String status);
}
