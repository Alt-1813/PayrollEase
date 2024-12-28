
package com.self.paytrack.attendance.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.self.paytrack.attendance.model.Attendance;
import com.self.paytrack.attendance.repo.AttendanceRepo;
import com.self.paytrack.employee.model.Employee;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepo attendanceRepo;


	public boolean markLogin(Long employeeId) {
		LocalDate today = LocalDate.now();

		Attendance attendance = attendanceRepo.findAttendanceByEmployeeIdAndDate(employeeId, today);

		if (attendance == null) {

			attendance = new Attendance();
			Employee emp = new Employee();
			emp.setEmployeeId(employeeId);
			attendance.setEmployee(emp);
			attendance.setDate(today);
			attendance.setLogintime(LocalDateTime.now());
			attendance.setLogin(true);
			attendance.setIdealTime(0.0);
			attendanceRepo.save(attendance);
		} else {

			if (attendance.getLogoutTime() != null) {

				LocalDateTime lastLogoutTime = attendance.getLogoutTime();
				LocalDateTime currentLoginTime = LocalDateTime.now();

				attendance.setUpdatedLoginTime(currentLoginTime);
				Duration idleDuration = Duration.between(lastLogoutTime, currentLoginTime);
				double idleTime = idleDuration.toHours() + (idleDuration.toMinutesPart() / 60.0);

				attendance.setIdealTime(attendance.getIdealTime() + idleTime);

				// attendance.setLogintime(currentLoginTime);

				attendance.setLogoutTime(null);
				attendance.setLogin(true);

				attendanceRepo.save(attendance);
			} else {

				attendance.setLogin(true);
				return true;
			}
		}

		return true;
	}

	public String markLogout(long employeeId) {
		
		LocalDate today = LocalDate.now();
		Optional<Attendance> attendanceOpt = attendanceRepo.findPresentByEmployeeIdAndDate(employeeId, today);

		if (attendanceOpt.isEmpty()) {
			return "No active session found for employee ID: " + employeeId;
		}

		Attendance attendance = attendanceOpt.get();

		if (attendance.getLogoutTime() != null) {
			return "Employee has already logged out for the day.";
		}

		LocalDateTime logoutTime = LocalDateTime.now();
		attendance.setLogoutTime(logoutTime);
		calculateWorkingHoursAndOvertime(attendance);

		attendanceRepo.save(attendance);

		return "Employee logged out successfully. Total working hours: " + attendance.getWorkingHours() + " hours.";
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void autoLogoutEmployees() {

		List<Attendance> pendingLogouts = attendanceRepo.findPendingLogout(true);

		for (Attendance attendance : pendingLogouts) {

			LocalDateTime logoutTime = LocalDateTime.now(); // 23:59:59
			attendance.setLogoutTime(logoutTime);

			calculateWorkingHoursAndOvertime(attendance);

			attendanceRepo.save(attendance);
		}

		System.out.println("Auto-logout completed. Working hours updated for pending sessions.");
	}

	private void calculateWorkingHoursAndOvertime(Attendance attendance) {
		if (attendance.getLogintime() != null && attendance.getLogoutTime() != null) {
			Duration duration = Duration.between(attendance.getLogintime(), attendance.getLogoutTime());
			double workingHours = duration.toHours() + (duration.toMinutesPart() / 60.0);
			attendance.setWorkingHours(workingHours);
			if (workingHours > 8) {
				attendance.setOverTimeHours(workingHours - 8);
			} else {
				System.out.println("login and log out time not found ");
			}
		}
	}

	public void calculateWorkingHours(Long employeeId) {

	}

}
