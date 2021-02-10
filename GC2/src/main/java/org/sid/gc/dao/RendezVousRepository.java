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

public interface RendezVousRepository extends JpaRepository<RendezVous, Integer>{
	public Page<RendezVous> findByMedecinContains(String mc, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDAte RendezVous u set u.medecin_id=:medecin_id, u.patient_id=:patient_id, "
			+ "u.date=:date, u.time=:time,u.medecin=:medecin, u.patient=:patient"
			+ " where u.id = :id")
	public int updateRendez(@Param("id") int id,@Param("medecin_id") int medecin_id,
			@Param("patient_id") int patient_id, @Param("date") Date date, 
			@Param("time") String time,@Param("medecin") String medecin,@Param("patient") String patient);
	
	@Query("SELECT r from RendezVous r where r.medecin_id = :medecin_id")
	public Page<RendezVous> getMedRendez(@Param("medecin_id") int medecin_id, Pageable pageable);


	
}
