package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.app.model.Grn;

public interface GrnRepository extends JpaRepository<Grn, Integer> {
	//duplicate
	@Query("SELECT COUNT(GN.grnCode) FROM Grn GN WHERE GN.grnCode=:code")
	 public Integer getGrnCodeCount(String code);

}
