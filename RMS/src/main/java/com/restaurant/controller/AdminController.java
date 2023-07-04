package com.restaurant.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.model.Admin;
import com.restaurant.model.Customer;
import com.restaurant.service.AdminService;
import com.restaurant.service.RegistrationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "admin")
public class AdminController {
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private AdminService adminService;
	
	
	@PostMapping("register")
	public boolean saveCustomer(@RequestBody Customer customer) {
		return registrationService.saveCustomer(customer);
	}
	
	@GetMapping("login")
	public String adminLogin() {
		return "adminLogin";
	}
	
	@PostMapping("login")
	public String adminLogin1(Admin admin, Model model, HttpServletRequest req,RedirectAttributes redirectAttributes) {
		Optional<Admin> obj = adminService.findByEmailAndPassword(admin.getEmail(), admin.getPassword());
		if (obj.isPresent()) {
			return "redirect:/home";
		} else {
			redirectAttributes.addAttribute("msg", "Invalid Login Credentials");
			return "redirect:/login";
		}
	}
	
	@RequestMapping("home")
	public String home(HttpSession session) {
		session.setAttribute("role", "Admin");
		return "adminHome";
	}
	
	@GetMapping("logout")
	public String logout(Model model, HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/home";
	}

}
