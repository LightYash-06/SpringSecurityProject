package com.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.spring.security.entity.Employee;

import jakarta.transaction.Transactional;

public interface EmpRepository extends JpaRepository<Employee, Integer>{
	
	
	public Employee findByEmail(String email);

	public Employee findByVerificationCode(String code);
    @Modifying
    @Transactional
    @Query("UPDATE Employee u SET u.failedAttempt = ?1 WHERE u.email = ?2")
	public void updateFailedAttempt(int attempt, String email);

}
