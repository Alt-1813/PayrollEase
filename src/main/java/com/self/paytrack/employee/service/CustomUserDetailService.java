
package com.self.paytrack.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.self.paytrack.employee.model.CustomUserDetails;
import com.self.paytrack.employee.model.User;
import com.self.paytrack.employee.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userRepo.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("NO USER PRESENT");
		}

		return new CustomUserDetails(user);

	}
}
