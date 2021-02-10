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

public interface TraitementRepository extends JpaRepository<Traitement, Integer>{
	public Page<Traitement> findByPatientContains(String mc, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDAte Traitement u set u.periode=:periode, u.nombre=:nombre, "
			+ "u.medicament_id=:medicament_id,u.patient_id=:patient_id"
			+ ",u.medicament=:medicament,u.patient=:patient,"
			+ "u.medecin=:medecin where u.id = :id")
	public int updateTraitement(@Param("id") int id, @Param("periode") String periode,
			@Param("nombre") String nombre,@Param("medicament_id") int medicament_id,
			@Param("patient_id") int patient_id,
			@Param("medicament") String medicament,@Param("patient") String patient,
			@Param("medecin") String medecin);
	
	@Query("SELECT p from Traitement p where p.medecin_id = :medecin_id")
	public Page<Traitement> getMedTraitement(@Param("medecin_id") int medecin_id, Pageable pageable);

}
