package com.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.app.model.Shipping;
import com.app.model.ShippingDtl;
public interface IShippingService {

	Integer saveShipping(Shipping sp);
	void updateShipping(Shipping sp);

	void deleteShipping(Integer id);
	Optional <Shipping> getOneShipping(Integer id);

	List <Shipping>getAllShippings();
	boolean isShippingExist(Integer id);
   
	//AJAX call
	boolean isShippingCodeExist(String shippingCode);
	//new method for edit
	boolean isShippingCodeExistForEdit(String code,Integer id);
	
	//pagination
	Page<Shipping>getAllShipping(Pageable pageable);
	
	// -----------Screen#2--------
		Integer saveShippingDtl(ShippingDtl dtl);

		List<ShippingDtl> getAllDtlsByShippingId(Integer shippingId);

		void updateStatusByShippinDtlId(String status, Integer id);
}
