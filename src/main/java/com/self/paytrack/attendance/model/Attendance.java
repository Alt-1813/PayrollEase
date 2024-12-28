package com.self.paytrack.attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.hibernate.annotations.Generated;
import org.springframework.beans.factory.annotation.Autowired;

import com.self.paytrack.employee.model.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ATTENDANCE")

public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = " Attendance_Id")
	private long attendanceId;

	@ManyToOne
	@JoinColumn(name = "employeeId", nullable = false)
	@Autowired
	private Employee employee;

	@Column(name = "Login_Time")
	private LocalDateTime logintime;

	@Column(name = "Updated_Login_Time")
	private LocalDateTime updatedLoginTime;

	@Column(name = "Logout_Time")
	private LocalDateTime logoutTime;

	@Column(name = "Working_Hours")
	private double workingHours;

	@Column(name = "Overtime_Hours")
	private double overTimeHours;

	@Column(name = "Ideal_Time")
	private double idealTime;

	@Column(name = "Date")
	private LocalDate date;
	
	@Column(name = "Active_Login_User")
	private boolean isLogin;

}
