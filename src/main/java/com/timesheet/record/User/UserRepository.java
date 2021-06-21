package com.timesheet.record.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query(value = "select a from User a where a.username = ?1")
	User findByUsername(String username);
}
