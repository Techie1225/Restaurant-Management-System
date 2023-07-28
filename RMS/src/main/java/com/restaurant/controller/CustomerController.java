package com.restaurant.controller;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.model.Customer;
import com.restaurant.model.ReservedTables;
import com.restaurant.service.CustomerService;
import com.restaurant.service.ReservedTablesService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ReservedTablesService reservedTablesService;
//	
//	@GetMapping("register")
//	public String customerRegister(Model model, HttpServletRequest req) {
//		return "customerRegistration";
//	}
//	
//	@PostMapping("register")
//	public String customerRegister1(Customer customer,Model model, HttpServletRequest req) {
//		Optional<Customer> check = customerService.findByEmailOrPhone(customer.getEmail(), customer.getPhone());
//		System.out.println(check);
//		if (check.isPresent()) {
//			model.addAttribute("msg", "Duplicate Details!!!.....");
//			return "customerRegistration";
//		} else {
//			model.addAttribute("msg", "Registered Successfully!!...");
//			model.addAttribute("color", "green");
//			customerService.saveCustomer(customer);
//			return "customerLogin";
//		}
//	}
	
	@GetMapping("login")
	public String customerLogin(Model model, HttpServletRequest req) {
		return "customerLogin";
	}
//	
//	@PostMapping("login")
//	public String customerLogin1(Customer customer, Model model, HttpServletRequest req,RedirectAttributes redirectAttributes,HttpSession session) {
//		System.out.println(customer);
//		Optional<Customer> obj = customerService.findByEmailAndPassword(customer.getEmail(), customer.getPassword());
//		if (obj.isPresent()) {
//			session.setAttribute("role", "Customer");
//			session.setAttribute("customerid", obj.get().get_id());
//			return "redirect:/customer/home";
//		} else {
//			model.addAttribute("msg", "Invalid Login Credentials");
//			model.addAttribute("color", "red");
//			return "customerLogin";
//		}
//	}
	
	@RequestMapping("home")
	public String home(HttpSession session) {
		
		return "customerHome";
	}
	
	@GetMapping("tablebooking")
	public String tablebooking() {
		return "tablebooking";
	}
	
//	@PostMapping("tablebooking")
//	public String tablebooking1(ReservedTables reserveTable, Model model, HttpServletRequest req,HttpSession session) {
//		reserveTable.setCustomer_id((ObjectId) session.getAttribute("customerid"));
//		reserveTable.setTable_number(2);
//		System.out.println(reserveTable);
//		reservedTablesService.reserveTable(reserveTable);
//		return "tablebooking";
//	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/home";
	}

}
