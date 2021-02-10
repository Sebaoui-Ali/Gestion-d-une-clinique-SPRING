package org.sid.gc.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "factures")
@Entity
@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Facture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int medicament_id;
	private int patient_id;
	private int medecin_id;
	private int traitement_id;
	private String medicament;
	private String patient;
	private String medecin;
	private String prix;
	
	
	@CreationTimestamp	
	private Timestamp date_ajout;
	
	@UpdateTimestamp
	private Timestamp date_update;
	
}
