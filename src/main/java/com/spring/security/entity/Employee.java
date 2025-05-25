package com.spring.security.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String department;
	private String role;
	private String email;
	private String password;
	private boolean enable;
	private String verificationCode;
	private boolean isAccountNonLocked;
	private int failedAttempt;
	private Date lockTime;
	
	public Employee(int id, String name, String department, String role, String email, String password, boolean enable,
			String verificationCode, boolean isAccountNonLocked, int failedAttempt, Date lockTime) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.role = role;
		this.email = email;
		this.password = password;
		this.enable = enable;
		this.verificationCode = verificationCode;
		this.isAccountNonLocked = isAccountNonLocked;
		this.failedAttempt = failedAttempt;
		this.lockTime = lockTime;
	}


	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}


	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}


	public int getFailedAttempt() {
		return failedAttempt;
	}


	public void setFailedAttempt(int failedAttempt) {
		this.failedAttempt = failedAttempt;
	}


	public Date getLockTime() {
		return lockTime;
	}


	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}


	public int getId() {
		return id;
	}
	
	
	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public String getVerificationCode() {
		return verificationCode;
	}


	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}


	public Employee(int id, String name, String department, String role, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.role = role;
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Employee(int id, String name, String department, String role) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.role = role;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", role=" + role + ", email="
				+ email + ", password=" + password + ", enable=" + enable + ", verificationCode=" + verificationCode
				+ ", isAccountNonLocked=" + isAccountNonLocked + ", failedAttempt=" + failedAttempt + ", lockTime="
				+ lockTime + ", isAccountNonLocked()=" + isAccountNonLocked() + ", getFailedAttempt()="
				+ getFailedAttempt() + ", getLockTime()=" + getLockTime() + ", getId()=" + getId() + ", isEnable()="
				+ isEnable() + ", getVerificationCode()=" + getVerificationCode() + ", getEmail()=" + getEmail()
				+ ", getPassword()=" + getPassword() + ", getName()=" + getName() + ", getDepartment()="
				+ getDepartment() + ", getRole()=" + getRole() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
