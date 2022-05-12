package com.in.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in.event.model.UserEvent;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
	
	@Query(value = "SELECT * FROM user_event where user_user_id=:user_user_id", nativeQuery = true)
	public List<UserEvent> getByUserId(@Param("user_user_id") Long userId);

}
