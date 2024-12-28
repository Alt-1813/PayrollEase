package com.self.paytrack.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.self.paytrack.employee.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
	
	public User findByUsername(String username);

}
