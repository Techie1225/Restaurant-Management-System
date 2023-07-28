package com.restaurant.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.model.Admin;
import com.restaurant.model.Menu;
import com.restaurant.model.Tables;
import com.restaurant.service.AdminService;
import com.restaurant.service.ItemService;
import com.restaurant.service.ReservedTablesService;
import com.restaurant.service.TableService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ReservedTablesService reservedTablesService;
	
	@Autowired
	private TableService tableService;
	
	@GetMapping("login")
	public String adminLogin() {
		return "adminLogin";
	}
	
	@PostMapping("login")
	public String adminLogin1(Admin admin, Model model, HttpServletRequest req,RedirectAttributes redirectAttributes) {
		Optional<Admin> obj = adminService.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
		System.out.println(obj);
		if (obj.isPresent()) {
			return "redirect:/admin/home";
		} else {
			model.addAttribute("msg", "Invalid Login Credentials");
			return "adminLogin";
		}
	}
	
	@RequestMapping("home")
	public String home(HttpSession session) {
		session.setAttribute("role", "Admin");
		return "adminHome";
	}
	
	@RequestMapping("menu")
	public String menu(Model model) {
		Iterable<Menu> allItems = itemService.findAllitems();
		model.addAttribute("allItems", allItems);
		return "item";
	}
	
	@PostMapping("menu")
	public String menu1(Menu menu,Model model, HttpServletRequest req) {
		System.out.println(menu);
		menu.setItem(menu.getItem().toUpperCase());
		Optional<Menu> check = itemService.findByitem(menu.getItem());
		Iterable<Menu> allItems = itemService.findAllitems();
		System.out.println(check);
		if (check.isPresent()) {
			model.addAttribute("msg", "Item Already Added!!...");
			model.addAttribute("color", "red");
			model.addAttribute("allItems", allItems);
			return "item";
		} else {
			model.addAttribute("msg", "Item added Successfully!!...");
			model.addAttribute("color", "green");
			model.addAttribute("allItems", allItems);
			itemService.saveItem(menu);
			return "item";
		}
	}
	
	@RequestMapping("tables")
	public String tables(Model model) {
		Iterable<Tables> allTables = tableService.findAllTables();
		System.out.println(allTables);
		model.addAttribute("allTables", allTables);
		return "viewTables";
	}
	
	@PostMapping("addTables")
	public String addTables(Tables table,Model model) {
		System.out.println(table);
		table.setStatus("Not Occupied");
		Optional<Tables> check = tableService.findByTableNumber(table.getTableNumber());
		Iterable<Tables> allTables = tableService.findAllTables();
		System.out.println(check);
		if (check.isPresent()) {
			model.addAttribute("msg", "Table Already Added!!...");
			model.addAttribute("color", "red");
			model.addAttribute("allTables", allTables);
			return "viewTables";
		} else {
			model.addAttribute("msg", "Table added Successfully!!...");
			model.addAttribute("color", "green");
			tableService.saveItem(table);
			allTables = tableService.findAllTables();
			model.addAttribute("allTables", allTables);
			return "viewTables";
		}
	}
	
	@GetMapping("addItems")
	public String addItems() {
		
		return "addTables";
	}
	
	
	
//	@RequestMapping("addTables")
//	public String addTables1(ReservedTables reservedTables,Model model, HttpServletRequest req) {
//		tableService.addTables(reservedTables);
//		return "viewTables";
//	}
	
	@GetMapping("logout")
	public String logout(Model model, HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/home";
	}

}
