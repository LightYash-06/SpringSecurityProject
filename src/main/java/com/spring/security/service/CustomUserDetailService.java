package com.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.spring.security.config.CustomUser;
import com.spring.security.entity.Employee;
import com.spring.security.repository.EmpRepository;
@Component
public class CustomUserDetailService implements UserDetailsService {
	
	public CustomUserDetailService(EmpRepository emprepo) {
		super();
		this.emprepo = emprepo;
	}
public CustomUserDetailService() {
		// TODO Auto-generated constructor stub
	}
/* private Employee emp; */
@Autowired
private EmpRepository emprepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	Employee emp =emprepo.findByEmail(email);
	if (emp==null) {
		throw new UsernameNotFoundException("Username not found");
		
	}else {
		return new CustomUser(emp);
	}
		
	}

}
