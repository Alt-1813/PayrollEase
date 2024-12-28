package com.self.paytrack.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.self.paytrack.employee.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	public Employee findById(long employeeId);

}
