package com.spring.security.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring.security.entity.Employee;

public class CustomUser implements UserDetails {
	@Autowired
    private Employee emp;
    

    public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	// Constructor to initialize emp
    public CustomUser(Employee emp) {
    	super();
        this.emp = emp;  // Initialize emp with the provided Employee object
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (emp != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(emp.getRole());
            return Arrays.asList(authority);
        }
        return Arrays.asList(); // Return an empty list if emp is null
    }

    @Override
    public String getPassword() {
        if (emp != null) {
            return emp.getPassword();  // Return the password if emp is not null
        }
        return null;  // Return null or throw an exception if emp is null
    }

    @Override
    public String getUsername() {
        if (emp != null) {
            return emp.getEmail();  // Return the email if emp is not null
        }
        return null;  // Return null or throw an exception if emp is null
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Default behavior, you can customize this
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Default behavior, you can customize this
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Default behavior, you can customize this
    }

    @Override
    public boolean isEnabled() {
        return emp.isEnable() ; // Default behavior, you can customize this
    }
}
