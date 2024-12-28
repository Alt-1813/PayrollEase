
package com.self.paytrack.payroll.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.self.paytrack.payroll.model.Payroll;

@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Long> {

	@Query("SELECT p FROM Payroll p WHERE p.employee.employeeId = :employeeId")
	Payroll findByEmployeeIdAndMonthYear(@Param("employeeId") Long employeeId, @Param("monthYear") String monthYear);

}
