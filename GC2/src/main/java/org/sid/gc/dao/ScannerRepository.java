package org.sid.gc.dao;

import java.util.Date;

import org.sid.gc.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ScannerRepository extends JpaRepository<Scanner, Integer>{
	public Page<Scanner> findByNomContains(String mc, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDAte Scanner u set u.nom=:nom, u.des=:des, "
			+ "u.date=:date, u.patient_id=:patient_id, u.patient=:patient where u.id = :id")
	public int updateScanner(@Param("id") int id, @Param("nom") String nom,
			@Param("des") String des,@Param("date") Date date,
			@Param("patient_id") int patient_id,@Param("patient") String patient);
	
}
