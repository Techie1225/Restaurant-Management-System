package com.restaurant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.model.Orders;
import com.restaurant.model.ReservedTab;
import com.restaurant.model.ReservedTables;
import com.restaurant.model.Waiter;
import com.restaurant.model.orderItems;
import com.restaurant.repo.ICustomerRepo;
import com.restaurant.repo.IOrdersRepo;
import com.restaurant.repo.IReservedTablesRepo;
import com.restaurant.repo.ITableRepo;
import com.restaurant.service.OdersService;
import com.restaurant.service.WaiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("waiter")
public class WaiterController {
	
	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private ICustomerRepo iCustomerRepo;
	
	@Autowired
	private IOrdersRepo iOrderRepo;
	
	@Autowired
	private IReservedTablesRepo iReservedTablesRepo;
	
	@Autowired
	private ITableRepo iTableRepo;
	
	@Autowired
	private OdersService odersService;
	
	@GetMapping("register")
	public String waiterRegister(Model model, HttpServletRequest req) {
		return "waiterRegistration";
	}
	
	@PostMapping("register")
	public String waiterRegister1(Waiter waiter,Model model, HttpServletRequest req) {
		Optional<Waiter> check = waiterService.findByUsername(waiter.getUsername());
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
	public String waiterLogin() {
		return "waiterLogin";
	}
	
	@PostMapping("login")
	public String waiterLogin1(Waiter waiter, Model model, HttpServletRequest req,RedirectAttributes redirectAttributes) {
		System.out.println(waiter);
		Optional<Waiter> obj = waiterService.findByUsernameAndPassword(waiter.getUsername(), waiter.getPassword());
		if (obj.isPresent()) {
			redirectAttributes.addAttribute("waiter_id", obj.get().get_id());
			return "redirect:/waiter/home";
		} else {
			model.addAttribute("msg", "Invalid Login Credentials");
			model.addAttribute("color", "red");
			return "waiterLogin";
		}
	}
	
	@GetMapping("home")
	public String home(HttpSession session, Model model,@ModelAttribute("waiter_id") Object waiter_id) {
		System.out.println(waiter_id);
		session.setAttribute("role", "Waiter");
		session.setAttribute("waiter_id", waiter_id);
		List<ReservedTables> li = waiterService.findAllTables();
		List<ReservedTab> lires = new ArrayList<ReservedTab>();
//		model.addAttribute("NR",li);
		model.addAttribute("waiter_id", waiter_id);
//		model.addAttribute("cust_name",cust_name(null));
//		model.addAttribute("table",table_number(null));
		ReservedTab rtd ;
		for(ReservedTables rt : li) {
			rtd = new ReservedTab();
			rtd.setReservedid(rt.get_id());
			rtd.setName(cust_name(rt.getCustomer_id()));
			rtd.setTable(table_number(rt.getReserved_table_id()));
			lires.add(rtd);
		}
		model.addAttribute("NR",lires);
		return "waiterHome";
	}
	
	private String table_number(ObjectId id) {
		return iTableRepo.findBy_id(id).getTableNumber();
	}

	private String cust_name(ObjectId id) {
		return iCustomerRepo.findBy_id(id).getName();
	}

	@GetMapping("assignTable")
	public String assignTable(@RequestParam("res_table_id") ObjectId res_table_id,@RequestParam("waiter_id") ObjectId waiter_id,Model model) {
		System.out.println(res_table_id);
		System.out.println(waiter_id);
		ReservedTables reservedTables = iReservedTablesRepo.findBy_id(res_table_id);
		reservedTables.setWaiter_id(waiter_id);
		iReservedTablesRepo.save(reservedTables);
		Orders order = new Orders();
		order.setReservedid(res_table_id);
		order.setWaiter_id(waiter_id);
		ObjectId orderid = odersService.saveOrders(order);
		model.addAttribute("orde_id", orderid);
		return "orders";
	}
	
	@GetMapping("orderitem")
	public String oderitem(HttpSession session, Model model,orderItems orderItems) {
		System.out.println("------------------------");
		System.out.println(orderItems);
		model.addAttribute("msg", "ordered Successfully");
		return "orders";
	}
	
	@GetMapping("inorders")
	public String inorders(HttpSession session, Model model) {
		System.out.println("------------------------");
		return "orders";
	}
	
	@GetMapping("assignedtable")
	public String assigntable(HttpSession session, Model model) {
		String id = (String) session.getAttribute("waiter_id");
		Iterable<ReservedTables> allreserved = iReservedTablesRepo.findAll();
		List<ReservedTab> lires = new ArrayList<ReservedTab>();
		ReservedTab rtd ;
		for(ReservedTables rt : allreserved) {
			if(rt.getWaiter_id()!=null && rt.getWaiter_id().toString().equals(id)) {
					rtd = new ReservedTab();
					rtd.setReservedid(rt.get_id());
					rtd.setOrderid(findorder(rt.get_id()));
					rtd.setName(cust_name(rt.getCustomer_id()));
					rtd.setTable(table_number(rt.getReserved_table_id()));
					lires.add(rtd);
				}
				
			}
		System.out.println(lires);
		model.addAttribute("NR",lires);
		return "assignedtable";
	}
	
	private ObjectId findorder(ObjectId id) {
		System.out.println("--------------------------------------");
		System.out.println(id);
		for(Orders od : iOrderRepo.findAll())
		{
			System.out.println(od);
			if(od.getReservedid().toString().equals(id.toString())) {
				System.out.println("innnnnnnn");
				return od.get_id();
				
			}
		}
		return null;
	}
	
	@GetMapping("/logout")
	public String waiterLogout(Model model, HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/home";
	}

}
