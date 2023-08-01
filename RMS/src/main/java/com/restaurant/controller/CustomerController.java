package com.restaurant.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping("convertTime")
	public LocalDateTime convertTime(@RequestBody Map<String, String> requestBody) {
		String timeInput = requestBody.get("to_date");

		// Parse the time string into a LocalTime object
		LocalTime localTime = LocalTime.parse(timeInput, DateTimeFormatter.ofPattern("HH:mm"));

		// Combine the LocalTime with the current date to create a LocalDateTime object
		return LocalDateTime.of(LocalDate.now(), localTime);
	}

	@RequestMapping("reserve")
	public String afterReserve(Customer customer, Model model, HttpServletRequest req) throws ParseException {
		System.out.println(customer);
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		// Parse the time string into a LocalTime object
		LocalTime localTime = LocalTime.parse(customer.getTo_date_convert(), DateTimeFormatter.ofPattern("HH:mm"));

		// Combine the LocalTime with the current date to create a LocalDateTime object
		customer.setTo_date(LocalDateTime.of(LocalDate.now(), localTime));
		Date userDate = parser.parse(customer.getTo_date_convert());
		customer.setTo_date_convert(null);
		System.out.println(customer);
		ObjectId custId = customerService.saveCustomer(customer);
		
		Date ten = parser.parse("10:00");
		Date nightten = parser.parse("22:00");
		    
		    if (userDate.after(ten) && userDate.before(nightten)) {
		    	
				if (custId != null) {
					model.addAttribute("msg", "Table Reserved!!...");
					model.addAttribute("color", "green");
					return "reserveTable";
				} else {
					model.addAttribute("msg", "Table Not Availble!!...");
					model.addAttribute("color", "red");
					return "reserveTable";
				}
		    }
		    else {
		    	model.addAttribute("msg", "Available from 10 AM to 10 PM");
				model.addAttribute("color", "red");
				return "reserveTable";
		    }
		
	}

}
