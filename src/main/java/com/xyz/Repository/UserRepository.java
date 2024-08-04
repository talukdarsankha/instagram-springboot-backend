package com.xyz.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByUsername(String username);
	
	@Query("SELECT DISTINCT u from User u WHERE u.email LIKE %:query% OR u.username LIKE %:query% OR u.name LIKE %:query%")       
	public List<User> searchUser(String query);
	
	

}
