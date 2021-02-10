package org.sid.gc.dao;

import java.util.Date;
import java.util.List;

import org.sid.gc.entities.RendezVous;
import org.sid.gc.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer>  {
	@Query("SELECT u from User u where u.username = :username")
	public User getUserByUsername(@Param("username") String username);
	
	@Query("SELECT users FROM User users INNER JOIN RoleUser users_roles ON users_roles.user_id = users.id WHERE users_roles.role_id=2")
	public Page<User> getMedecin( String mc, Pageable pageable);
	
	@Query("SELECT users FROM User users INNER JOIN RoleUser users_roles ON users_roles.user_id = users.id WHERE users_roles.role_id=2")
	public List<User> getMedecins();

	@Query("SELECT users FROM User users INNER JOIN RoleUser users_roles ON users_roles.user_id = users.id WHERE users_roles.role_id=2 AND "
			+ "(users.username like %:m% OR users.nom like %:m% OR users.prenom like %:m% )")
	public Page<User> getMedecinK(@Param("m") String mc, Pageable pageable);
	
	@Query("SELECT users FROM User users INNER JOIN RoleUser users_roles ON users_roles.user_id = users.id WHERE users_roles.role_id=3 AND "
			+ "(users.username like %:m% OR users.nom like %:m% OR users.prenom like %:m% )")
	public Page<User> getGPK(@Param("m") String mc, Pageable pageable);
	
	@Query("SELECT users FROM User users INNER JOIN RoleUser users_roles ON users_roles.user_id = users.id WHERE users_roles.role_id=3")
	public Page<User> getGP(String mc, Pageable pageable);
	
		
//	@Query("SELECT r from RendezVous r where r.medecin_id = :medecin_id")
//	public User getMedRendez(@Param("medecin_id") int medecin_id);
	
	
//	@Query("SELECT MAX(u.id) from User u")
//	public int getUserLastId();
	
	@Transactional
	@Modifying
	@Query("DELEte from User u where u.id = :id")
	public int deleteUser(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query("DELEte from RoleUser u where u.user_id = :id")
	public int deleteRoleUser(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query("UPDAte User u set u.username=:username, u.nom=:nom, u.prenom=:prenom, u.date_naiss=:date_naiss, "
			+ "u.genre=:genre, u.tel=:tel, u.address = :address, u.email=:email, u.cin=:cin where u.id = :id")
	public int updateUser(@Param("id") Integer id,@Param("username") String username,@Param("nom") String nom,
			@Param("prenom") String prenom,@Param("date_naiss") Date date_naiss,@Param("genre") String genre,
			@Param("tel") String tel,@Param("address") String address,@Param("email") String email,@Param("cin") String cin);

	

}
