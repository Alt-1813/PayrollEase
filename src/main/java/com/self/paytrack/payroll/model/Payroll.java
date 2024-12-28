
package com.self.paytrack.payroll.model;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAYROLL")

public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Payroll_Id")
	private int payrollId;

	@ManyToOne
	@JoinColumn(name = "employeeId")
    @Autowired
	private Employee employee;

	@Column(name = "Base_Salary")
	private double baseSalary;

	@Column(name = "Overtime_Pay")
	private double overtimePay;

	@Column(name = "Tax_Deduction")
	private double taxDeduction;

	@Column(name = "Bonuses")
	private double bonuses;

	@Column(name = "Final_Salary")
	private double finalSalary;
	


}
