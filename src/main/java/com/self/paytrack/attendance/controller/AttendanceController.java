
package com.self.paytrack.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.self.paytrack.attendance.service.AttendanceService;

@RestController
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@GetMapping("/login/{employeeId}")
	public void markLogin(@PathVariable Long employeeId) {

		attendanceService.markLogin(employeeId);

	}

	@PostMapping("/logout/{employeeId}")
	public ResponseEntity<String> logout(@PathVariable Long employeeId) {
		try {
			String message = attendanceService.markLogout(employeeId);
			return ResponseEntity.ok(message);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error during logout: " + e.getMessage());
		}
	}

	@PostMapping("/auto-logout")
	public ResponseEntity<String> triggerAutoLogout() {
		try {
			attendanceService.autoLogoutEmployees();
			return ResponseEntity.ok("Auto-logout task triggered successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error during auto-logout: " + e.getMessage());
		}
	}

	@GetMapping("/workinghours/{employeeId}")
	public void calculateWorkingHours(@PathVariable Long employeeId) {
		attendanceService.calculateWorkingHours(employeeId);

	}

}
