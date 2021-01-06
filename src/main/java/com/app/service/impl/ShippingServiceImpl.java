package com.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Shipping;
import com.app.model.ShippingDtl;
import com.app.repo.ShippingDtlRepository;
import com.app.repo.ShippingRepository;
import com.app.service.IShippingService;
@Service
public class ShippingServiceImpl implements IShippingService {

	@Autowired
	private ShippingRepository repo;
	@Autowired
	private ShippingDtlRepository dtlrepo;

	@Override
	@Transactional
	public Integer saveShipping(Shipping sp) {
		Integer	id = repo.save(sp).getId();
		return id;
	}

	@Override
	@Transactional
	public void updateShipping(Shipping sp) {
		repo.save(sp);
	}

	@Override
	@Transactional
	public void deleteShipping(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Shipping> getOneShipping(Integer id) {
		Optional<Shipping> opt = repo.findById(id);
		return opt;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Shipping> getAllShippings() {
		List<Shipping> list = repo.findAll();
		return list;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean isShippingExist(Integer id) {
		boolean exist = repo.existsById(id);
		return exist;
	}

	//pagination
	@Override
	@Transactional(readOnly = true)
	public Page<Shipping> getAllShipping(Pageable pageable) {
		return repo.findAll(pageable);
	}
	//AJAX CALL
	@Override
	@Transactional(readOnly = true)
	public boolean isShippingCodeExist(String shippingCode) {
		int count = repo.getShippingCodeCount(shippingCode);
		boolean flag = (count>0 ? true: false);
		return flag;
	}
	//new method for Edit
	@Override
	@Transactional(readOnly = true)
	public boolean isShippingCodeExistForEdit(String code, Integer id) {
		int count = repo.getShippingCodeCountForEdit(code, id);
		boolean flag = (count>0 ? true: false);
		return flag;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer saveShippingDtl(ShippingDtl dtl) {
		return dtlrepo.save(dtl).getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShippingDtl> getAllDtlsByShippingId(Integer shippingId) {
		return dtlrepo.getAllDtlsByShippingId(shippingId);
	}

	@Override
	@Transactional
	public void updateStatusByShippinDtlId(String status, Integer id) {
		dtlrepo.updateStatusByShippinDtlId(status, id);
	}
}
