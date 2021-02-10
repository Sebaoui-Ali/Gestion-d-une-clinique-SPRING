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

public interface FactureRepository extends JpaRepository<Facture, Integer>{
	public Page<Facture> findByPatientContains(String mc, Pageable pageable);
	
}
