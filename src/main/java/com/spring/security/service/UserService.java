package com.spring.security.service;

import com.spring.security.entity.Employee;

public interface UserService {
	
	
	public Employee saveEmployee(Employee emp, String url);

	
	public void sendEmail(Employee emp, String url);
	
	public boolean verifyAccount(String verificationCode);
	
	public void increaseFailedAttempt(Employee user);

	public void resetAttempt(String email);

	public void lock(Employee user);

	public boolean unlockAccountTimeExpired(Employee user);
}
