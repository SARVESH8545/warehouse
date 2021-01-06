package com.app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.PurchaseDetail;
import com.app.model.PurchaseOrder;

public interface IPurchaseOrderService {
	Integer savePurchaseOder(PurchaseOrder po);
	void updatePurchaseOrder(PurchaseOrder po);

	void deletePurchaseOrder(Integer id);
	Optional<PurchaseOrder> getOnePurchaseOrder(Integer id);

	List<PurchaseOrder> getAllPurchaseOrders();
	boolean isPurchaseOrderExist(Integer id);

	boolean isPurchaseOrderCodeExist(String orderCode); 
	public List<Object[]>getQualityCheckCount();
	
	//pagination
	Page<PurchaseOrder> getAllPurchaseOrders(Pageable pageable);
	
	
	//Screen#2 Operations
	Integer addPartToPo(PurchaseDetail  dtl);
	List<PurchaseDetail> getPurchaseDtlWithPoId(Integer purchaseId);
	void deletePurchaseDtl(Integer id);
	void updatePurchaseOrderStatus(String status,Integer id);
	Integer getPurchaseDtlCountWithPoId(Integer purchaseId);
	Map<Integer,String>getPoIdAndCodeByStatus(String status);

}
