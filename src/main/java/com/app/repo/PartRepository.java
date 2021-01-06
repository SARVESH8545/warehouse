package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.model.Part;

public interface PartRepository extends JpaRepository<Part, Integer>
{
	@Query("SELECT COUNT(PT.partCode) FROM Part PT WHERE PT.partCode=:code")
	 public Integer getPartCodeCount(String code);
	
	@Query("SELECT P.id,P.partCode FROM Part P")
	public List<Object[]> getPartIdAndCode();
	
	@Query("SELECT COUNT(P.partCode) FROM Part P WHERE P.partCode=:code and P.id!=:id")
	public Integer getPartCodeCountForEdit(String code,Integer id);

}
