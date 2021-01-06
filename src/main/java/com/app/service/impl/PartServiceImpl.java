package com.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Part;
import com.app.repo.PartRepository;
import com.app.service.IPartService;
@Service
public class PartServiceImpl implements IPartService {
	
    @Autowired
	private PartRepository repo;
    
	@Override
	@Transactional
	public Integer savePart(Part pt) {
		 Integer id=repo.save(pt).getId();
		return id;
	}

	@Override
	@Transactional
	public void updatePart(Part pt) {
		repo.save(pt);
	}

	@Override
	@Transactional
	public void deletePart(Integer id) {
		repo.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Part> getOnePart(Integer id) {
		Optional<Part> opt=repo.findById(id);
				return opt;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Part> getAllParts() {
	List<Part>list=repo.findAll();
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPartExist(Integer id) {
		boolean exist=repo.existsById(id);
				return exist;
	}

	@Override
	public boolean isPartCodeExist(String partCode) {
		int count=repo.getPartCodeCount(partCode);
		boolean flag=(count>0 ? true : false);
		return flag;
		}

	@Override
	@Transactional(readOnly = true)
	public boolean isPartCodeExistForEdit(String partCode, Integer id) {
		return (repo.getPartCodeCountForEdit(partCode,id))>0?true:false;
	}

	@Override
	public Page<Part> getAllParts(Pageable pageable) {
		return repo.findAll(pageable);
	}
	
	@Override
	  public Map<Integer, String> getPartIdAndCode() {
		return repo.getPartIdAndCode()
			.stream()
			.collect(Collectors.toMap(
	  ob->Integer.valueOf(ob[0].toString()), 
	  ob->ob[1].toString())
			);
	  }
}
