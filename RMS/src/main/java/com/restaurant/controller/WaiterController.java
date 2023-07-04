package com.restaurant.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.model.Waiter;
import com.restaurant.service.WaiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "waiter")
public class WaiterController {
	
	@Autowired
	private WaiterService waiterService;
	
	@GetMapping("register")
	public String waiterRegister(Model model, HttpServletRequest req) {
		return "waiterRegistration";
	}
	
	@PostMapping("register")
	public String waiterRegister1(Waiter waiter,Model model, HttpServletRequest req) {
		Optional<Waiter> check = waiterService.findByEmailOrPhone(waiter.getEmail(), waiter.getPhone());
		System.out.println(check);
		if (check.isPresent()) {
			model.addAttribute("msg", "Duplicate Details!!!.....");
			return "waiterRegistration";
		} else {
			model.addAttribute("msg", "Registered Successfully!!...");
			model.addAttribute("color", "green");
			waiterService.saveCustomer(waiter);
			return "waiterLogin";
		}
	}
	
	@GetMapping("login")
	public String waiterLogin(Model model, HttpServletRequest req) {
		return "waiterLogin";
	}
	
	@PostMapping("login")
	public String waiterLogin1(Waiter waiter, Model model, HttpServletRequest req,RedirectAttributes redirectAttributes) {
		Optional<Waiter> obj = waiterService.findByEmailAndPassword(waiter.getEmail(), waiter.getPassword());
		if (obj.isPresent()) {
			return "redirect:/home";
		} else {
			redirectAttributes.addAttribute("msg", "Invalid Login Credentials");
			redirectAttributes.addAttribute("color", "red");
			return "redirect:/login";
		}
	}
	
	@GetMapping("home")
	public String home(HttpSession session) {
		session.setAttribute("role", "Waiter");
		return "adminHome";
//		return "waiterHome";
	}
	
	@GetMapping("/logout")
	public String waiterLogout(Model model, HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/home";
	}

}
