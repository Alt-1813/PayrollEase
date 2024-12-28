
package com.self.paytrack.attendance.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.self.paytrack.attendance.model.Attendance;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

	@Query("SELECT a FROM Attendance a WHERE a.employee.employeeId = :employeeId AND a.date = :date")
	Attendance findAttendanceByEmployeeIdAndDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);
	
	

	@Query("SELECT a FROM Attendance a WHERE a.isLogin = :isLogin")
	List<Attendance> findPendingLogout(@Param("isLogin") boolean isLogin);

	@Query("SELECT a FROM Attendance a WHERE a.employee.employeeId = :employeeId")
	Optional<Attendance> findMostRecentSessionByEmployeeId(long employeeId);
	
	@Query("SELECT a FROM Attendance a WHERE a.employee.employeeId = :employeeId AND a.date = :date")
	Optional<Attendance> findPresentByEmployeeIdAndDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);

	@Query("SELECT a FROM Attendance a WHERE a.employee.employeeId = :employeeId AND a.date BETWEEN :startDate AND :endDate")
	List<Attendance> findByEmployeeIdAndDateBetween(@Param("employeeId") Long employeeId,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
