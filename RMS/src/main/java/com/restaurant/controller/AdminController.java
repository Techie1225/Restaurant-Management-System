package com.restaurant.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.model.Admin;
import com.restaurant.model.Menu;
import com.restaurant.model.ReservedTab;
import com.restaurant.model.ReservedTables;
import com.restaurant.model.Tables;
import com.restaurant.repo.ICustomerRepo;
import com.restaurant.repo.IMenuRepo;
import com.restaurant.repo.IReservedTablesRepo;
import com.restaurant.repo.ITableRepo;
import com.restaurant.service.AdminService;
import com.restaurant.service.ItemService;
import com.restaurant.service.ReservedTablesService;
import com.restaurant.service.TableService;
import com.restaurant.service.WaiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ICustomerRepo iCustomerRepo;
	
	@Autowired
	private ITableRepo iTableRepo;
	
	@Autowired
	private IMenuRepo iItemRepo;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private ReservedTablesService reservedTablesService;
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private IReservedTablesRepo iReservedTablesRepo;
	
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
	public String home(HttpSession session,Model model) {
		session.setAttribute("role", "Admin");
		List<ReservedTables> li = waiterService.findAllTables();
		List<ReservedTab> lires = new ArrayList<ReservedTab>();
//		model.addAttribute("NR",li);
//		model.addAttribute("waiter_id", session.getAttribute("waiter_id"));
//		model.addAttribute("cust_name",cust_name(null));
//		model.addAttribute("table",table_number(null));
		ReservedTab rtd;
		for (ReservedTables rt : li) {
			rtd = new ReservedTab();
			rtd.setReservedid(rt.get_id());
			rtd.setName(cust_name(rt.getCustomerid()));
			rtd.setTable(table_number(rt.getReserved_table_id()));
			rtd.setCustid(rt.getCustomerid());
			lires.add(rtd);
		}
		model.addAttribute("NR", lires);
		return "adminHome";
	}
	
	@GetMapping("removeTable")
	public String removeTable(@RequestParam("res_table_id") ObjectId res_table_id, Model model) {
		System.out.println(res_table_id);
//		System.out.println(waiter_id);
		ReservedTables reservedTables = iReservedTablesRepo.findBy_id(res_table_id);
//		reservedTables.setWaiter_id(waiter_id);
		iReservedTablesRepo.delete(reservedTables);
		iCustomerRepo.deleteBy_id(reservedTables.getCustomerid());
		Tables tab = iTableRepo.findBy_id(reservedTables.getReserved_table_id());
		tab.setStatus("Not Occupied");
		iTableRepo.save(tab);
		return "redirect:/admin/home";
	}
	
	
	private String table_number(ObjectId id) {
		return iTableRepo.findBy_id(id).getTableNumber();
	}

	private String cust_name(ObjectId id) {
		return iCustomerRepo.findBy_id(id).getName();
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
		Iterable<Menu> allItems;
		System.out.println(check);
		if (check.isPresent()) {
			Menu updateitem = check.get();
			updateitem.setPrice(menu.getPrice());
			itemService.saveItem(updateitem);
			System.out.println(check);
			model.addAttribute("msg", "Item Updated Successfully!!...");
			model.addAttribute("color", "green");
			allItems = itemService.findAllitems();
			model.addAttribute("allItems", allItems);
			return "item";
		} else {
			model.addAttribute("msg", "Item added Successfully!!...");
			model.addAttribute("color", "green");
			itemService.saveItem(menu);
			allItems = itemService.findAllitems();
			model.addAttribute("allItems", allItems);
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
	
	@GetMapping("deletetable")
	public String deletetable(HttpSession session,@RequestParam("table_id") ObjectId table_id, Model model) {
		System.out.println(table_id);
		Tables tables = iTableRepo.deleteBy_id(table_id);
		return "redirect:/admin/tables";
	}
	
	@GetMapping("deleteitem")
	public String deleteitem(HttpSession session,@RequestParam("menu_id") ObjectId menu_id, Model model) {
//		System.out.println(menu_id);
		Menu tables = iItemRepo.deleteBy_id(menu_id);
		return "redirect:/admin/menu";
	}
	
	@PostMapping("addTables")
	public String addTables(Tables table,Model model) {
		System.out.println(table);
		table.setStatus("Not Occupied");
		Optional<Tables> check = tableService.findByTableNumber(table.getTableNumber());
		Iterable<Tables> allTables ;
		System.out.println(check);
		if (check.isPresent()) {
			System.out.println(check);
			Tables updatetable = check.get();
			updatetable.setCapacity(table.getCapacity());
			tableService.saveItem(updatetable);
			model.addAttribute("msg", "Table Updated Successfully!!...");
			model.addAttribute("color", "green");
			allTables = tableService.findAllTables();
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
