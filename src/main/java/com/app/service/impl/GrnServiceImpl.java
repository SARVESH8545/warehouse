package com.app.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app.model.Grn;
import com.app.model.GrnDtl;
import com.app.repo.GrnRepository;
import com.app.repo.GrnDtlRepository;
import com.app.service.IGrnService;
@Service
public class GrnServiceImpl implements IGrnService {

	@Autowired
	private GrnRepository repo;
	
	@Autowired
	private GrnDtlRepository dtlRepo;

	@Transactional
	@Override
	public Integer saveGrn(Grn grn) {
		Integer id =repo.save(grn).getId();
		return id;
	}

	@Transactional
	@Override
	public void updateGrn(Grn grn) {
		repo.save(grn);	
	}

	@Transactional
	@Override
	public void deleteGrn(Integer id) {
		repo.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Grn> getOneGrn(Integer id) {
		Optional<Grn>opt = repo.findById(id);
		return opt;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Grn> getAllGrns() {
		List<Grn>list=repo.findAll();
		return list;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isGrnExist(Integer id) {
		boolean	exist=repo.existsById(id);
		return exist;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Grn> getAllGrn(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Integer saveGrnDtl(GrnDtl dtl) {
		return dtlRepo.save(dtl).getId();
	}

	@Override
	public List<GrnDtl> getAllDtlsByGrnId(Integer grnId) {
		return dtlRepo.getAllBtlsByGrnId(grnId);
	}

	@Override
	@Transactional
	public void updateStatusByGrnDtlId(String status, Integer id) {
		dtlRepo.updateStatusByGrnDtlId(status, id);		
	}
}
