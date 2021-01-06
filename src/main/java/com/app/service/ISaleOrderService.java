package com.app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.SaleDtl;
import com.app.model.SaleOrder;

public interface ISaleOrderService {
	Integer saveSaleOrder(SaleOrder order);

	void updateSaleOrder(SaleOrder order);

	void deleteSaleOrder(Integer id);

	Optional<SaleOrder> getOneSaleOrder(Integer id);

	List<SaleOrder> getAllSaleOrders();

	boolean isSaleOrderExist(Integer id);

	boolean isSaleOrderCodeExist(String orderCode);

	List<Object[]> getStockModeCount();
	
	//pagination
	Page<SaleOrder>getAllSaleOrders(Pageable pageable);
	
	/**
	 * Screen#2 operations
	 */
	public Integer addPartToSo(SaleDtl dtl);

	List<SaleDtl> getSaleDtlWithSoId(Integer saleId);

	void deleteSaleDtl(Integer id);

	void updateSaleOrderStatus(String status, Integer id);

	Integer getSaleDtlCountWithSoId(Integer saleId);
	
	Map<Integer,String> getSoIdAndCodeByStatus(String status);

}
