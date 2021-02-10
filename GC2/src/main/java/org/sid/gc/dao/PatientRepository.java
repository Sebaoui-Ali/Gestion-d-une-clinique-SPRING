package org.sid.gc.dao;

import java.util.List;

import org.sid.gc.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PatientRepository extends JpaRepository<Patient, Integer>{
	public Page<Patient> findByNomContains(String mc, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDAte Patient u set u.nom=:nom, u.prenom=:prenom, u.age=:age, "
			+ "u.genre=:genre, u.tel=:tel, u.address = :address, u.email=:email, u.cin=:cin, "
			+ "u.maladie=:maladie, u.docteur_id=:docteur_id where u.id = :id")
	public int updatePatient(@Param("id") int id,@Param("nom") String nom,
			@Param("prenom") String prenom,@Param("age") int age,@Param("genre") String genre,@Param("cin") String cin ,
			@Param("tel") String tel,@Param("address") String address,@Param("email") String email,
			@Param("maladie") String maladie,@Param("docteur_id") int docteur_id);

	@Query("SELECT p from Patient p where p.docteur_id = :docteur_id")
	public Page<Patient> getMedPatient(@Param("docteur_id") int docteur_id, Pageable pageable);

	@Query("SELECT r from Patient r where r.id = :id")
	public List<Patient> getPatients(@Param("id") int id);
}
