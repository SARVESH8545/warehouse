package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.model.Uom;

public interface UomRepository extends JpaRepository<Uom, Integer> {
	@Query("SELECT UT.uomType, count(UT.uomType) FROM Uom UT GROUP BY UT.uomType")
	public List<Object[]> getUomTypeCount();
	
	@Query("SELECT id,uomModel FROM Uom")
	List<Object[]> getUomIdAndModel();
}
