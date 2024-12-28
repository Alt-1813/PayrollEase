package com.self.paytrack.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.self.paytrack.employee.model.Employee;
import com.self.paytrack.employee.service.EmployeeService;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();

	}

	@PostMapping("/createEmployee")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);

	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {

		Employee employee = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employee);

	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeReq) {

		Employee employeeSave = employeeService.updateEmployee(id, employeeReq);
		return ResponseEntity.ok(employeeReq);

	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
		try {
			employeeService.deleteEmployee(id);
			return ResponseEntity.ok("Employee Deleted Successfully");
		} catch (RuntimeException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}

	}

}
