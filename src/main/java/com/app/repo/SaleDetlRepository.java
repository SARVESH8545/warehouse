package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.model.SaleDtl;
public interface SaleDetlRepository extends JpaRepository<SaleDtl, Integer> {

	@Query("SELECT SDTL FROM SaleDtl SDTL INNER JOIN SDTL.so AS SO WHERE SO.id=:saleId")
	List<SaleDtl> getSaleDtlWithSoId(Integer saleId);

	@Query("SELECT count(SDTL.id) FROM SaleDtl SDTL INNER JOIN SDTL.so AS SO WHERE SO.id=:saleId")
	public Integer getSaleDtlCountWithSoId(Integer saleId);

}
