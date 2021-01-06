package com.app.service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.ShipmentType;

public interface IShipmentTypeService {

	Integer saveShipmentType(ShipmentType st);
	void updateShipmentType(ShipmentType st);
	
	void deleteShipmentType(Integer id);
	Optional<ShipmentType> getOneShipmentType(Integer id);
	
	List<ShipmentType> getAllShipmentTypes();
	boolean isShipmentTypeExist(Integer id);
	//AJAX CALL
	boolean isShipmentTypeCodeExist(String shipmentCode);
	//new method for edit
	boolean isShipmentTypeCodeExistForEdit(String code,Integer id);
	
	
	public List<Object[]> getShipmentModeCount();
	public Map<Integer, String>getShipmentIdAndCode();
	
	//pagination
	Page<ShipmentType>getAllShipmentTypes(Pageable pageable);
}
