package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.model.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
	//for duplicate AJAX call
	@Query("SELECT COUNT(SP.shippingCode) FROM Shipping SP WHERE SP.shippingCode=:code")
	public Integer getShippingCodeCount(String code);

	//new one for Edit checking
	@Query("SELECT COUNT(SP.shippingCode) FROM Shipping SP WHERE SP.shippingCode=:code and SP.id!=:id ")
	public Integer getShippingCodeCountForEdit(String code,Integer id);
}
