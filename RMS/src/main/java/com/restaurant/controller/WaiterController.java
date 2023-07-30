package com.restaurant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.restaurant.model.Billing;
import com.restaurant.model.Menu;
import com.restaurant.model.Orders;
import com.restaurant.model.Payment;
import com.restaurant.model.ReservedTab;
import com.restaurant.model.ReservedTables;
import com.restaurant.model.Waiter;
import com.restaurant.model.itemsquantity;
import com.restaurant.model.orderItems;
import com.restaurant.repo.IBillRepo;
import com.restaurant.repo.ICustomerRepo;
import com.restaurant.repo.IMenuRepo;
import com.restaurant.repo.IOrdersRepo;
import com.restaurant.repo.IPaymentRepo;
import com.restaurant.repo.IReservedTablesRepo;
import com.restaurant.repo.ITableRepo;
import com.restaurant.service.OdersService;
import com.restaurant.service.WaiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

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
	private IMenuRepo iMenuRepo;

	@Autowired
	private IBillRepo iBillRepo;

	@Autowired
	private IPaymentRepo iPaymentRepo;

	@Autowired
	private OdersService odersService;

	@GetMapping("register")
	public String waiterRegister(Model model, HttpServletRequest req) {
		return "waiterRegistration";
	}

	@PostMapping("register")
	public String waiterRegister1(Waiter waiter, Model model, HttpServletRequest req) {
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
	public String waiterLogin1(Waiter waiter, Model model, HttpServletRequest req,
			RedirectAttributes redirectAttributes, HttpSession session) {
		System.out.println(waiter);
		Optional<Waiter> obj = waiterService.findByUsernameAndPassword(waiter.getUsername(), waiter.getPassword());
		if (obj.isPresent()) {
			redirectAttributes.addAttribute("waiter_id", obj.get().get_id());
			session.setAttribute("role", "Waiter");
			session.setAttribute("waiter_id", obj.get().get_id().toString());
			return "redirect:/waiter/home";
		} else {
			model.addAttribute("msg", "Invalid Login Credentials");
			model.addAttribute("color", "red");
			return "waiterLogin";
		}
	}

	@GetMapping("home")
	public String home(HttpSession session, Model model) {
//		if(session.getAttribute("role")==null) {
//			System.out.println("in sesssionnnn");
//			
//		}
		List<ReservedTables> li = waiterService.findAllTables();
		List<ReservedTab> lires = new ArrayList<ReservedTab>();
//		model.addAttribute("NR",li);
		model.addAttribute("waiter_id", session.getAttribute("waiter_id"));
//		model.addAttribute("cust_name",cust_name(null));
//		model.addAttribute("table",table_number(null));
		ReservedTab rtd;
		for (ReservedTables rt : li) {
			rtd = new ReservedTab();
			rtd.setReservedid(rt.get_id());
			rtd.setName(cust_name(rt.getCustomer_id()));
			rtd.setTable(table_number(rt.getReserved_table_id()));
			lires.add(rtd);
		}
		model.addAttribute("NR", lires);
		return "waiterHome";
	}

	private String table_number(ObjectId id) {
		return iTableRepo.findBy_id(id).getTableNumber();
	}

	private String cust_name(ObjectId id) {
		return iCustomerRepo.findBy_id(id).getName();
	}

	@GetMapping("assignTable")
	public String assignTable(@RequestParam("res_table_id") ObjectId res_table_id,
			@RequestParam("waiter_id") ObjectId waiter_id, Model model) {
		System.out.println(res_table_id);
		System.out.println(waiter_id);
		ReservedTables reservedTables = iReservedTablesRepo.findBy_id(res_table_id);
		reservedTables.setWaiter_id(waiter_id);
		iReservedTablesRepo.save(reservedTables);
		Orders order = new Orders();
		order.setReservedid(res_table_id);
		order.setWaiter_id(waiter_id);
		order.setItemsid(null);
		ObjectId orderid = odersService.saveOrders(order);
		model.addAttribute("orderid", orderid);
		model.addAttribute("menu", iMenuRepo.findAll());
		return "orders";
	}

	@GetMapping("orderitem")
	public String oderitem(HttpSession session, Model model, orderItems orderItems) {
		int quantity;
		float totalprice = 0;
		System.out.println("------------------------");
		System.out.println(orderItems);
		Orders cust_order = iOrderRepo.findAllBy_id(orderItems.getId());
		System.out.println(cust_order);
		Map<String, Map<String, Integer>> itemsid = cust_order.getItemsid();
		if (itemsid!=null && itemsid.containsKey(orderItems.getItemid())) {
			quantity = orderItems.getQuantity() + itemsid.get(orderItems.getItemid()).get("quantity");
			System.out.println(quantity);
			itemsid.get(orderItems.getItemid()).put("quantity", quantity);
			cust_order.setItemsid(itemsid);
			
			iOrderRepo.save(cust_order);
		} else {
			if(itemsid==null) {
				itemsid=new HashMap<String, Map<String, Integer>>();
			}
			Map<String, Integer> quanmap = new HashMap<String, Integer>();
			quanmap.put("quantity", orderItems.getQuantity());
			itemsid.put(orderItems.getItemid(), quanmap);
			System.out.println(itemsid);
			cust_order.setItemsid(itemsid);
			iOrderRepo.save(cust_order);
		}
		model.addAttribute("orderid", orderItems.getId());
		model.addAttribute("msg", "ordered Successfully");
		model.addAttribute("menu", iMenuRepo.findAll());
		List<itemsquantity> showitem = new ArrayList<>();
		itemsid = cust_order.getItemsid();
		itemsquantity iq = new itemsquantity();
		for (String key : itemsid.keySet()) {
			System.out.println(key + ":" + itemsid.get(key));
			iq = new itemsquantity();
			Menu itemprice = findprices(new ObjectId(key));
			iq.setItem(itemprice.getItem());
			iq.setPrice((float) (itemprice.getPrice() * itemsid.get(key).get("quantity")));
			totalprice += (float) (itemprice.getPrice() * itemsid.get(key).get("quantity"));
			iq.setQuantity(itemsid.get(key).get("quantity"));
			showitem.add(iq);
		}
		model.addAttribute("items", showitem);
		model.addAttribute("totalprice", totalprice);
		System.out.println(itemsid);
		System.out.println(showitem);
		return "orders";
	}

	public Menu findprices(ObjectId id) {
		return iMenuRepo.findBy_id(id);
	}

	@GetMapping("inorders")
	public String inorders(@RequestParam("order_id") ObjectId order_id, @RequestParam("cust_id") ObjectId custid,
			Model model, HttpSession session) {
		float totalprice = 0;
		System.out.println(order_id);
		model.addAttribute("orderid", order_id);
		model.addAttribute("menu", iMenuRepo.findAll());
		List<itemsquantity> showitem = new ArrayList<>();
		Orders cust_order = iOrderRepo.findAllBy_id(order_id);
		Map<String, Map<String, Integer>> itemsid = cust_order.getItemsid();
		itemsquantity iq = new itemsquantity();
		if (itemsid == null) {

		} else {
			for (String key : itemsid.keySet()) {
				System.out.println(key + ":" + itemsid.get(key));
				iq = new itemsquantity();
				Menu itemprice = findprices(new ObjectId(key));
				iq.setItem(itemprice.getItem());
				iq.setPrice((float) (itemprice.getPrice() * itemsid.get(key).get("quantity")));
				totalprice += (float) (itemprice.getPrice() * itemsid.get(key).get("quantity"));
				iq.setQuantity(itemsid.get(key).get("quantity"));
				showitem.add(iq);
			}
		}
		model.addAttribute("items", showitem);
		model.addAttribute("totalprice", totalprice);
		model.addAttribute("order_id", order_id);
		model.addAttribute("cust_id", custid);
		System.out.println(itemsid);
		System.out.println(showitem);
		session.setAttribute("order_id", order_id);
		session.setAttribute("custid", custid);
		return "orders";
	}

	@GetMapping("assignedtable")
	public String assigntable(HttpSession session, Model model) {
		String id = (String) session.getAttribute("waiter_id");
		Iterable<ReservedTables> allreserved = iReservedTablesRepo.findAll();
		List<ReservedTab> lires = new ArrayList<ReservedTab>();
		ReservedTab rtd;
		for (ReservedTables rt : allreserved) {
			if (rt.getWaiter_id() != null && rt.getWaiter_id().toString().equals(id)) {
				rtd = new ReservedTab();
				rtd.setReservedid(rt.get_id());
				rtd.setOrderid(findorder(rt.get_id()));
				rtd.setName(cust_name(rt.getCustomer_id()));
				rtd.setTable(table_number(rt.getReserved_table_id()));
				rtd.setCustid(rt.getCustomer_id());
				lires.add(rtd);
			}

		}
		System.out.println(lires);
		model.addAttribute("NR", lires);
		return "assignedtable";
	}

	private ObjectId findorder(ObjectId id) {
		System.out.println("--------------------------------------");
		System.out.println(id);
		for (Orders od : iOrderRepo.findAll()) {
			System.out.println(od);
			if (od.getReservedid().toString().equals(id.toString())) {
				System.out.println("innnnnnnn");
				return od.get_id();
			}
		}
		return null;
	}

	@GetMapping("/payment")
	public String payment(@RequestParam("split") String split, @RequestParam("totalprice") String totalprice,
			Model model, HttpServletRequest req, HttpSession session) {
		System.out.println(split);
		System.out.println(totalprice);
		Billing bill = new Billing();
		bill.setAmount(Float.parseFloat(totalprice));
		bill.setSplit(Integer.parseInt(split));
		bill.setOrder_id(new ObjectId(session.getAttribute("order_id").toString()));
		ObjectId id = iBillRepo.save(bill).get_id();
//		session.setAttribute("billid", id);

		return "payment";
	}

	@RequestMapping("payment")
	public String payment1(Payment payment, Model model, HttpServletRequest req, HttpSession session) {

		payment.setCustomer_id(new ObjectId(session.getAttribute("custid").toString()));
		payment.setOrder_id(new ObjectId(session.getAttribute("order_id").toString()));
		System.out.println(payment);
		iPaymentRepo.save(payment);
		return "waiterHome";
	}

	@GetMapping("/logout")
	public String waiterLogout(Model model, HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/home";
	}

}
