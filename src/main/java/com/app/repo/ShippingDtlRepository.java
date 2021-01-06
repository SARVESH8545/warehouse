package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.model.ShippingDtl;

public interface ShippingDtlRepository extends JpaRepository<ShippingDtl, Integer> {

	@Query("SELECT dtl FROM ShippingDtl dtl INNER JOIN dtl.shipping AS shipping WHERE shipping.id=:shippingId")
	public List<ShippingDtl> getAllDtlsByShippingId(Integer shippingId);

//@Query + @Modifying ==> non- select
	@Modifying
	@Query("UPDATE ShippingDtl set status=:status WHERE id=:id")
	public void updateStatusByShippinDtlId(String status, Integer id);
}
