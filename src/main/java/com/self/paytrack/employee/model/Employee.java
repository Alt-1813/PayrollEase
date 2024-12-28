package com.self.paytrack.employee.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEE_DETAILS")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="EMPLOYEE_ID")
	private long employeeId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL_NAME")
	private String emailId;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE_NAME")
	private long phoneNo;

	@Column(name = "DATE_OF_JOINING")
	private Date dateOfJoining;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "SALARY")
	private double salary;

}
