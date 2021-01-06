package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.app.model.OrderMethod;

public interface OrderMethodRepository extends JpaRepository<OrderMethod, Integer> ,
JpaSpecificationExecutor<OrderMethod>{
	
	@Query("SELECT COUNT(OM.orderCode) FROM OrderMethod OM WHERE OM.orderCode=:code")
	public Integer getOrderMethodCodeCount(String code);

	//for edit 
	@Query("select count(om.orderCode) from OrderMethod om where om.orderCode=:code and om.id!=:id")
	public Integer getOrderMethodCodeCountForEdit(String code,Integer id);

	@Query("SELECT OM.orderMode, count(OM.orderMode) FROM OrderMethod OM GROUP BY OM.orderMode")
	public List<Object[]> getOrderModeCount();
}
