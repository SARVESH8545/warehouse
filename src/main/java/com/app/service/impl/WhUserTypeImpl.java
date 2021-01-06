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

import com.app.model.WhUserType;
import com.app.repo.WhUserTypeRepository;
import com.app.service.IWhUserTypeService;
@Service
public class WhUserTypeImpl implements IWhUserTypeService {
	@Autowired
	private WhUserTypeRepository repo;//HAS-A


	@Override
	@Transactional
	public Integer saveWhUserType(WhUserType wut) {
		return repo.save(wut).getId();
	}

	@Override
	@Transactional
	public void updateWhUserType(WhUserType wut) {
		repo.save(wut);
	}

	@Override
	@Transactional
	public void deleteWhUserType(Integer id) {
		repo.deleteById(id);	
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<WhUserType> getOneWhUserType(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WhUserType> getAllWhUserTypes() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isWhUserTypeExist(Integer id) {
		return repo.existsById(id);
	}
	@Override
	public boolean isWhUserTypeEmailExist(String mail) {
		return repo.getWhUserTypeCount(mail)>0?true:false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getWhUserTypeCount() {
		return repo.getWhUserTypeCount();
	}
	@Override
	@Transactional(readOnly = true)
	public Map<Integer, String> getWhUserTypeIdAndCode(String userType) {
		return repo.getWhUserTypeIdAndCode(userType)
				.stream()
				.collect(Collectors.toMap(
						ob->Integer.valueOf(ob[0].toString()),
						ob->ob[1].toString())
						);
	}
	//pagination
	@Transactional(readOnly = true)
	@Override
	public Page<WhUserType> getAllWhUserTypes(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
