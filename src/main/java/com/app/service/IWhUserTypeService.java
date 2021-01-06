package com.app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.WhUserType;
public interface IWhUserTypeService {
	
	Integer saveWhUserType(WhUserType wut);
	void updateWhUserType(WhUserType wut);

	void deleteWhUserType(Integer id);
	Optional<WhUserType> getOneWhUserType(Integer id);

	List<WhUserType> getAllWhUserTypes();
	boolean isWhUserTypeExist(Integer id);
	
	boolean isWhUserTypeEmailExist(String mail);
	public List<Object[]> getWhUserTypeCount();
	
	public Map<Integer, String>getWhUserTypeIdAndCode(String userType);
	//pagination
	Page<WhUserType> getAllWhUserTypes(Pageable pageable);

}
