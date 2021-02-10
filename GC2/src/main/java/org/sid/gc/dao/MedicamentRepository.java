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

public interface MedicamentRepository extends JpaRepository<Medicament, Integer>{
	public Page<Medicament> findByNameContains(String mc, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDAte Medicament u set u.name=:name, u.caract=:caract, "
			+ "u.quantite=:quantite where u.id = :id")
	public int updateMedicament(@Param("id") int id, @Param("name") String name,
			@Param("caract") String caract,@Param("quantite") String quantite);
	
}
