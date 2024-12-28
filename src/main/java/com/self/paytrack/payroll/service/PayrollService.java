
package com.self.paytrack.payroll.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.self.paytrack.attendance.model.Attendance;
import com.self.paytrack.attendance.repo.AttendanceRepo;
import com.self.paytrack.employee.model.Employee;
import com.self.paytrack.employee.repo.EmployeeRepo;
import com.self.paytrack.payroll.model.Payroll;
import com.self.paytrack.payroll.repo.PayrollRepo;

@Service
public class PayrollService {

	@Autowired
	private PayrollRepo payrollRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private AttendanceRepo attendanceRepo;

	public Employee getEmployeeRecords(Long employeeId, String monthYear) {
		// Fetch the employee by ID
		Optional<Employee> employee = employeeRepo.findById(employeeId);

		// If employee is not found, throw an exception
		if (employee == null) {
			throw new IllegalArgumentException("Employee not found");
		}

		return employee.orElseThrow(() -> new IllegalArgumentException("Employee not found"));

	}

	public Payroll generatePayslip(Long employeeId) {

		List<Attendance> attendanceRecords = getAttendanceListforEmployee(employeeId);

		double baseSalary = attendanceRecords.get(0).getEmployee().getSalary();
		System.out.println(attendanceRecords);

		double totalWorkingHours = attendanceRecords.stream()
				.collect(Collectors.summingDouble(Attendance::getWorkingHours));
		double totalOverTimeHour = attendanceRecords.stream()
				.collect(Collectors.summingDouble(Attendance::getOverTimeHours));
		// double baseSalary = payroll.getBaseSalary();
		Payroll payroll = new Payroll();
		payroll = calculateSalaryFromAttendance(baseSalary, totalWorkingHours, totalOverTimeHour, payroll);
		payroll = calculateTaxDeduction(baseSalary, payroll);

		return payrollRepo.save(payroll);
	}

	private Payroll calculateSalaryFromAttendance(double baseSalary, double totalWorkingHours,
			double totalOverTimeHours, Payroll payroll) {
		double hourlySalary = (baseSalary / (365 * 8));

		payroll.setBaseSalary(hourlySalary * totalWorkingHours);
		payroll.setOvertimePay(hourlySalary * totalOverTimeHours);

		return payroll;
	}

	private Payroll calculateTaxDeduction(double salary, Payroll payroll) {
		double calculatedWithOverTime = payroll.getBaseSalary() + payroll.getOvertimePay();
		double tax = 0;

		if (salary <= 250000) {
			tax = 0;
		} else if (salary >= 250000 && salary < 500000) {
			tax = calculatedWithOverTime * 0.05;

		} else if (salary >= 500000 && salary < 750000) {
			tax = calculatedWithOverTime * 0.10;

		} else if (salary >= 750000 && salary < 1000000) {
			tax = calculatedWithOverTime * 0.15;
		} else if (salary >= 1000000 && salary < 1500000) {
			tax = calculatedWithOverTime * 0.20;
		} else {
			tax = calculatedWithOverTime * 0.30;
		}

		payroll.setTaxDeduction(tax);
		payroll.setFinalSalary(calculatedWithOverTime - tax);
		return payroll;

	}

	private double payableSalary(double attendanceSalary, double overtimePay, double taxDeduction) {
		return attendanceSalary + overtimePay - taxDeduction;
	}

	private List<Attendance> getAttendanceListforEmployee(Long employeeId) {
		// Get the current date
		LocalDate currentDate = LocalDate.now();

		// Get the first day of the current month
		LocalDate startOfMonth = currentDate.withDayOfMonth(1);

		// Get the last day of the current month
		LocalDate endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

		// Print the results
		System.out.println("Start of the Month: " + startOfMonth);
		System.out.println("End of the Month: " + endOfMonth);

		return attendanceRepo.findByEmployeeIdAndDateBetween(employeeId, startOfMonth, endOfMonth);
	}
}
