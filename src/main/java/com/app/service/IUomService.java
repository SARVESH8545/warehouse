package com.app.service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.Uom;

public interface IUomService 
{
	Integer saveUom(Uom um);
	void updateUom(Uom um);
	
	void deleteUom(Integer id);
	Optional <Uom> getOneUom(Integer id);
	
	List <Uom>getAllUoms();
	boolean isUomExist(Integer id);
	
	public List<Object[]> getUomTypeCount();
	Map<Integer, String>getUomIdAndModel();
	
	Page<Uom> getAllUoms(Pageable pageable);
}
