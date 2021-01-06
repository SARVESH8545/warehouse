package com.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.OrderMethod;
import com.app.repo.OrderMethodRepository;
import com.app.service.IOrderMethodService;
@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

	@Autowired
	private OrderMethodRepository repo;

	@Override
	@Transactional
	public Integer saveOrderMethod(OrderMethod om) {
		Integer id = repo.save(om).getId();
		return id;
	}
	
	@Override
	@Transactional	
	public void updateOrderMethod(OrderMethod om) {
		repo.save(om);
	}


	@Override
	@Transactional
	public void deleteOrderMethod(Integer id) {
		repo.deleteById(id);
	}

		
	@Override
	@Transactional(readOnly = true)
	public Optional<OrderMethod> getOneOrderMethod(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderMethod> getAllOrderMethods() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isOrderMethodeExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isOrderMethodCodeExist(String code) {
		int count=repo.getOrderMethodCodeCount(code);
		boolean flag=(count>0 ? true : false);
		return flag;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getOrderModeCount() {
		return repo.getOrderModeCount();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<OrderMethod> getAllOrderMethods(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public boolean isOrderMethodCodeExistEdit(String code, Integer id) {
		int count = repo.getOrderMethodCodeCountForEdit(code, id);
		return count>0 ? true:false;
	}
}