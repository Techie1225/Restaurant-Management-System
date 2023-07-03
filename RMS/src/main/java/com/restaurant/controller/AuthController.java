package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurant.model.Registration;
import com.restaurant.service.RegistrationService;

@Controller
@RequestMapping(value = "rms")
public class AuthController {
	
	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping()
	public String home() {
		return "home";
	}
	
	@PostMapping("register")
	public boolean saveCustomer(@RequestBody Registration customer) {
		return registrationService.saveCustomer(customer);
	}
	
	@GetMapping("adminLogin")
	public String adminLogin() {
		return "adminLogin";
	}
	
	@PostMapping("adminLogin")
	public String adminLogin1() {
		return "adminHome";
	}

	@GetMapping("customerLogin")
	public String customerLogin() {
		return "customerLogin";
	}
	
	@PostMapping("customerLogin")
	public String customerLogin1() {
		return "adminHome";
	}

}
