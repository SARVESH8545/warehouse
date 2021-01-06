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

import com.app.model.PurchaseDetail;
import com.app.model.PurchaseOrder;
import com.app.repo.PurchaseDetailRepository;
import com.app.repo.PurchaseOrderRepository;
import com.app.service.IPurchaseOrderService;
@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository repo;

	@Autowired
	private PurchaseDetailRepository dtlRepo;

	@Transactional
	@Override
	public Integer savePurchaseOder(PurchaseOrder po) {
		Integer id=repo.save(po).getId();
		return id;
	}

	@Transactional
	@Override
	public void updatePurchaseOrder(PurchaseOrder po) {
		repo.save(po);	
	}

	@Transactional
	@Override
	public void deletePurchaseOrder(Integer id) {
		repo.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<PurchaseOrder> getOnePurchaseOrder(Integer id) {
		Optional<PurchaseOrder> opt=repo.findById(id);		
		return opt;
	}

	@Transactional(readOnly = true)
	@Override
	public List<PurchaseOrder> getAllPurchaseOrders() {
		List<PurchaseOrder>list = repo.findAll();
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isPurchaseOrderExist(Integer id) {
		boolean exist = repo.existsById(id);
		return exist;
	}

	@Override
	public List<Object[]> getQualityCheckCount() {
		return repo.getQualityCheckCount();
	}
	@Override
	public boolean isPurchaseOrderCodeExist(String orderCode) {
		return repo.getPurchaseOrderCodeCount(orderCode)>0?true:false;
	}

	@Override
	public Page<PurchaseOrder> getAllPurchaseOrders(Pageable pageable) {

		return repo.findAll(pageable);
	}
	//-------------------screen#2-------
	@Override
	@Transactional
	public Integer addPartToPo(PurchaseDetail dtl) {
		return dtlRepo.save(dtl).getId();
	}

	@Override
	public List<PurchaseDetail> getPurchaseDtlWithPoId(Integer purchaseId) {
		return dtlRepo.getPurchaseDtlWithPoId(purchaseId);
	}

	@Override
	@Transactional
	public void deletePurchaseDtl(Integer id) {
		dtlRepo.deleteById(id);
	}


	@Override
	@Transactional
	public void updatePurchaseOrderStatus(String status, Integer id) {
		repo.updatePurchaseOrderStatus(status, id);
	}

	@Override
	public Integer getPurchaseDtlCountWithPoId(Integer purchaseId) {
		return dtlRepo.getPurchaseDtlCountWithPoId(purchaseId);
	}

	@Override
	public Map<Integer, String>getPoIdAndCodeByStatus(String status) 
	{
		return repo.getPoIdAndCodeByStatus(status)
				.stream().collect(Collectors.toMap(
						ob->Integer.valueOf(ob[0].toString()),
						ob->ob[1].toString()));
	}
}
