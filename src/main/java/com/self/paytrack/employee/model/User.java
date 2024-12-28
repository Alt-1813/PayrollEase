package com.self.paytrack.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {
	
	@Id
	@Column(name = "UserName")
	private String username;
	
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "Role")
	private String role;

}
