package com.restaurant.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurant.model.Customer;
import com.restaurant.service.CustomerService;
import com.restaurant.service.ReservedTablesService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ReservedTablesService reservedTablesService;

	
	@GetMapping("reserve")
	public String beforeReserve(Model model, HttpServletRequest req) {
		return "reserveTable";
	}

	
	@RequestMapping("reserve")
	public String afterReserve(Customer customer,Model model, HttpServletRequest req) {
		System.out.println(customer);
		ObjectId custId = customerService.saveCustomer(customer);
		if(custId!=null) {
			model.addAttribute("msg", "Table Reserved!!...");
			model.addAttribute("color", "green");
			return "reserveTable";
		}
		else {
			model.addAttribute("msg", "Table Not Availble!!...");
			model.addAttribute("color", "red");
			return "reserveTable";
		}
	}

}
