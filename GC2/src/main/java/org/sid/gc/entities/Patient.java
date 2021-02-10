package org.sid.gc.entities;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Required;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	@NotNull
	@NotNull
	@Size(min = 2, max = 255)
	private String nom;
	
//	@NotNull
	@NotNull
	@Size(min = 2, max = 255)
	private String prenom;
	
	@NotNull
	@Size(min = 2, max = 255)
	@Email
	private String email;
	@NotNull
	@Size(min = 2, max = 255)
	private String tel;
	@NotNull
	@Size(min = 2, max = 255)
	private String genre;
	@NotNull
	@Size(min = 2, max = 255)
	private String maladie;
	@NotNull
	@Size(min = 2, max = 255)
	private String address;
	@NotNull
	@Size(min = 2, max = 255)
	private String cin;
	
	@CreationTimestamp
	private Timestamp date_ajout;
	
	@UpdateTimestamp
	private Timestamp date_update;
	
	@NotNull
	@DecimalMin("1")
	private int age;
	
	private int docteur_id;
	
}
