package com.self.paytrack.employee.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.self.paytrack.employee.model.Employee;
import com.self.paytrack.employee.repo.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	// @GetMapping For ALl the Employees List
	public List<Employee> getAllEmployees() {

		return employeeRepo.findAll();

	}

	public Employee createEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}

	public Employee getEmployeeById(Long id) {
		Optional<Employee> employee = employeeRepo.findById(id);
		if (employee.isPresent()) {
			return employee.get();

		} else {
			throw new RuntimeException("Employee not found by ID:" + id);

		}

	}

	public Employee updateEmployee(Long id, Employee employeeReq) {

		Optional<Employee> employeeOptional = employeeRepo.findById(id);
		if (employeeOptional.isPresent()) {
			Employee employeeDb = employeeOptional.get();
			employeeDb.setEmailId(employeeReq.getEmailId());
			employeeDb.setAddress(employeeReq.getAddress());
			return employeeRepo.save(employeeDb);

		} else {
			throw new RuntimeException("Employee not found by ID:" + id);
		}

	}

	public void deleteEmployee(Long id) {
		Optional<Employee> employeeOptional = employeeRepo.findById(id);
		if (!employeeOptional.isPresent()) {
			throw new RuntimeException("Employee not present :" + id);
		}
		Employee employee = employeeOptional.get();
		if ("nagpur".equalsIgnoreCase(employee.getAddress())) {
			throw new RuntimeException("cannot delete the employee details as the city is NAGPUR");
		}

		employeeRepo.deleteById(id);

	}

}
