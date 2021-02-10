package org.sid.gc.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_roles")
@AllArgsConstructor
@NoArgsConstructor

public class RoleUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer role_id;

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_id;
	
	public RoleUser(int i, int j) {
		this.user_id=i;
		this.role_id=j;
	}
}
