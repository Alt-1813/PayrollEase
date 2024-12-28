
package com.self.paytrack.payroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.self.paytrack.employee.model.Employee;
import com.self.paytrack.payroll.model.Payroll;
import com.self.paytrack.payroll.service.PayrollService;

@RestController
public class PayrollController {

	@Autowired
	private PayrollService payrollService;

	@GetMapping("/employee-records")
	public ResponseEntity<Employee> getEmployeeRecords(@RequestParam Long employeeId, @RequestParam String monthYear) {
		try {
			Employee employee = payrollService.getEmployeeRecords(employeeId, monthYear);
			return ResponseEntity.ok(employee);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/generate-payslip/{employeeId}")
	public ResponseEntity<Payroll> generatePayslip(@PathVariable Long employeeId) {
		try {
			Payroll payroll = payrollService.generatePayslip(employeeId);
			return ResponseEntity.ok(payroll);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
