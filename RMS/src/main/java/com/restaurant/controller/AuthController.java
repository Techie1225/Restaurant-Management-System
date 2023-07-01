package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Registration;
import com.restaurant.service.RegistrationService;

@RestController
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

}
