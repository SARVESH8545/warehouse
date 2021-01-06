package com.app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.Part;

public interface IPartService {
	
	
	Integer savePart(Part pt);
	void updatePart(Part pt);
	
	void deletePart(Integer id);
	Optional<Part> getOnePart(Integer id);
	
	List<Part> getAllParts();
	boolean isPartExist(Integer id);
	
	boolean isPartCodeExist(String partCode);
	boolean isPartCodeExistForEdit(String partCode,Integer id);

	
	//pagination
	public Page<Part> getAllParts(Pageable pageable );
	Map<Integer,String> getPartIdAndCode();
}
