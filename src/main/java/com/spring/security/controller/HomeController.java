package com.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.security.entity.Employee;
import com.spring.security.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
@Controller
public class HomeController {
	@Autowired
	 private UserService service;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@RequestMapping("/")
	public String index()
	{
		
		return "index";
	}
	@RequestMapping("/invalid")
	public String invalid()
	{
		
		return "invalid";
	}
	@RequestMapping("/profile")
	public String profile()
	{
		
		return "profile";
	}
	@RequestMapping("/signin")
	public String signin()
	{
		
		return "login";
	}

	
	@RequestMapping("/home")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String home()
	{
		
		return "home";
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@RequestMapping("/about")
	public String about()
	{
		
		return "about";
	}
	@PostMapping("/Register")
	public String saveuser(@ModelAttribute Employee emp, HttpSession session, Model m, HttpServletRequest request  )
	{
		
		// System.out.println(user);
		String url = request.getRequestURL().toString();

		// System.out.println(url); http://localhost:8080/saveUser
		url = url.replace(request.getServletPath(), "");
		// System.out.println(url);
		// //http://localhost:8080/verify?code=3453sdfsdcsadcscd

		Employee e2 = service.saveEmployee(emp,url);
		if (e2 !=null) {
			System.out.println(".....................Registration Sucessfull.....................");
			
		} else {
System.out.println("........................Shamnya...................");
		}
		return "redirect:/Register";
	}
    // ✅ Serve the registration form when a user visits /register
    @GetMapping("/Register")
    public String showRegistrationForm() {
        return "Register"; // Make sure register.html is inside src/main/resources/templates/
    }
    @GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model m) {
		boolean f = service.verifyAccount(code);

		if (f) {
			m.addAttribute("msg", "Sucessfully your account is verified");
		} else {
			m.addAttribute("msg", "may be your vefication code is incorrect or already veified ");
		}

		return "message";
	}

}
