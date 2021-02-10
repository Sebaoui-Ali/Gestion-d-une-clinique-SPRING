package org.sid.gc.dao;

import org.sid.gc.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role, Integer> {

//	@Query("SELECT u from Role u where u.name = :name")
	public Role findByName(String name);
}
