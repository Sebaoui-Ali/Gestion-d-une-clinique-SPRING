package org.sid.gc.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "medicaments")
@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Medicament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String quantite;
	private String caract;
	
	
	
	
	
	
	
	
	
	
	
	
}
