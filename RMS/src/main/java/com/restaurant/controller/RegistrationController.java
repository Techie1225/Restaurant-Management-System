package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurant.service.CustomerService;

@Controller
@RequestMapping(value = "rms")
public class RegistrationController {
	
	@Autowired
	private CustomerService customerService;
	
	public String saveCustomer() {
		return null;
	}
	

}
