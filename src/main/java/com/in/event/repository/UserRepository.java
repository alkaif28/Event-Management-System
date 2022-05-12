package com.in.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.in.event.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM user where email=:email", nativeQuery = true)
	public User getUserByName(String email);

	public Boolean existsByEmail(String email);

}
