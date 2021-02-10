package org.sid.gc.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "traitements")
@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Traitement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String periode;
	private int medicament_id;
	private int patient_id;
	private int medecin_id;
	private String nombre;
	private String medicament;
	private String patient;
	private String medecin;
	
	
	
	
	
}
