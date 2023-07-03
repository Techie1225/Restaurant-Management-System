package com.restaurant.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurant.model.Admin;
import com.restaurant.model.Customer;
import com.restaurant.service.AdminService;
import com.restaurant.service.RegistrationService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "rms")
public class AdminController {
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private AdminService adminService;
	
	
	@RequestMapping()
	public String home() {
		return "home";
	}
	
	@PostMapping("register")
	public boolean saveCustomer(@RequestBody Customer customer) {
		return registrationService.saveCustomer(customer);
	}
	
	@GetMapping("adminLogin")
	public String adminLogin() {
		return "adminLogin";
	}
	
	@PostMapping("adminLogin")
	public String adminLogin1(Admin admin, Model model, HttpServletRequest req) {
		Optional<Admin> obj = adminService.findByEmailAndPassword(admin.getEmail(), admin.getPassword());
		if (obj.isPresent()) {
			return "adminHome";
		} else {
			model.addAttribute("msg", "Invalid Login Credentials");
			return "adminLogin";
		}
	}

}
