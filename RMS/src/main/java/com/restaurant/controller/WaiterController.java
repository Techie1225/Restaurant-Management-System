package com.restaurant.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.restaurant.model.Billing;
import com.restaurant.model.ChangePassword;
import com.restaurant.model.Menu;
import com.restaurant.model.Orders;
import com.restaurant.model.ReservedTab;
import com.restaurant.model.ReservedTables;
import com.restaurant.model.Spliting;
import com.restaurant.model.Tables;
import com.restaurant.model.Waiter;
import com.restaurant.model.itemsquantity;
import com.restaurant.model.orderItems;
import com.restaurant.model.split;
import com.restaurant.repo.IBillRepo;
import com.restaurant.repo.ICustomerRepo;
import com.restaurant.repo.IMenuRepo;
import com.restaurant.repo.IOrdersRepo;
import com.restaurant.repo.IPaymentRepo;
import com.restaurant.repo.IReservedTablesRepo;
import com.restaurant.repo.ITableRepo;
import com.restaurant.repo.IWaiterRepo;
import com.restaurant.service.OdersService;
import com.restaurant.service.WaiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("waiter")
public class WaiterController {
	
	@Autowired
	private IWaiterRepo iWaiterRepo;

	@Autowired
	private WaiterService waiterService;

	@Autowired
	private ICustomerRepo iCustomerRepo;
	
	@Autowired
	private ITableRepo iTableRepo;

	@Autowired
	private IOrdersRepo iOrderRepo;

	@Autowired
	private IReservedTablesRepo iReservedTablesRepo;

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
			waiter.setStatus("change");
			waiterService.saveCustomer(waiter);
			return "waiterRegistration";
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
		if(obj.isPresent() && ("change").equals(obj.get().getStatus())) {
			waiter=obj.get();
			model.addAttribute("waiterid",waiter.get_id());
			return "changePassword";
		}
		else if (obj.isPresent()) {
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

	@PostMapping("changepassword")
	public String changepassword(ChangePassword changepassword, Model model) {
		System.out.println(changepassword.getWaiterid());
		System.out.println(changepassword);
		Optional<Waiter> obj = iWaiterRepo.findBy_id(changepassword.getWaiterid());
		Waiter waiter = obj.get();
		if(waiter.getPassword().equals(changepassword.getCurrentpassword())) {
			waiter.setPassword(changepassword.getNewpassword());
			waiter.setStatus(null);
			iWaiterRepo.save(waiter);
			return "waiterLogin";
		}
		else {
			
			model.addAttribute("waiterid",waiter.get_id());
			return "changePassword";
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
			rtd.setName(cust_name(rt.getCustomerid()));
			rtd.setTable(table_number(rt.getReserved_table_id()));
			rtd.setCustid(rt.getCustomerid());
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
	public String assignTable(HttpSession session,@RequestParam("res_table_id") ObjectId res_table_id,@RequestParam("cust_id") ObjectId cust_id,
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
		session.setAttribute("order_id", orderid);
		session.setAttribute("custid", cust_id);
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
		System.out.println(allreserved);
		List<ReservedTab> lires = new ArrayList<ReservedTab>();
		ReservedTab rtd;
		for (ReservedTables rt : allreserved) {
			if (rt.getWaiter_id() != null && rt.getWaiter_id().toString().equals(id) && rt.getStatus()==null) {
				System.out.println("customerrrrrrrrrrrrrrrrrrrrrrrrrr");
				System.out.println(rt.getCustomerid());
				rtd = new ReservedTab();
				rtd.setReservedid(rt.get_id());
				rtd.setOrderid(findorder(rt.get_id()));
				rtd.setName(cust_name(rt.getCustomerid()));
				rtd.setTable(table_number(rt.getReserved_table_id()));
				rtd.setCustid(rt.getCustomerid());
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

//	@RequestParam("split") String split,
	@GetMapping("/payment")
	public String payment( @RequestParam("totalprice") String totalprice,@RequestParam("split") String split,
			Model model, HttpServletRequest req, HttpSession session) {
		System.out.println(split);
		System.out.println(totalprice);
		Billing bill = new Billing();
		bill.setAmount(Float.parseFloat(totalprice));
		bill.setSplit(1);
		bill.setOrder_id(new ObjectId(session.getAttribute("order_id").toString()));
		
		ObjectId id = iBillRepo.save(bill).get_id();
//		session.setAttribute("billid", id);
		model.addAttribute("splitnumber", split);
		model.addAttribute("splitname", "split");
		return "split";
	}
	
	@RequestMapping("split")
	public String split(split splt, Model model, HttpServletRequest req, HttpSession session) {
		System.out.println(splt);
		String s;
		List<Float> l= new ArrayList<>();
		l.add(new Float(2));
		l.add(splt.getSplit1());
		l.add(splt.getSplit2());
		l.add(splt.getSplit3());
		l.add(splt.getSplit4());
		l.add(splt.getSplit5());
		Map<String,Float> splitmap =new HashMap<String,Float>();
		for(Integer i=1;i<=splt.getSplitnumber();i++) {
			s="split"+i.toString();
			splitmap.put(s, l.get(i));
		}
		System.out.println(splitmap);
		model.addAttribute("splitmap", splitmap);
//		model.addAttribute("splt",splt);
		return "selectsplit";
	}
	
	@GetMapping("split1")
	public String split(split splt, Model model, HttpServletRequest req, HttpSession session,@RequestParam("splitmap") String mapstring) {
		StringBuilder sb = new StringBuilder(mapstring);
		String st=sb.substring(1,sb.length()-1);
		System.out.println(st);
		Map splitmap = new HashMap();
		System.out.println("------------------------------");
		 String[] elements = st.split(",");
		 for(String s1: elements) {
		     String[] keyValue = s1.split("=");
		     splitmap.put(keyValue[0], keyValue[1]);
		 }
		System.out.println(splitmap);
		model.addAttribute("splitmap", splitmap);
		return "selectsplit";
	}
	
	@RequestMapping("paymentsplit")
	public String paymentsplit(Model model, HttpServletRequest req, HttpSession session,Spliting splting) {
//		Class<split> c= (Class<com.restaurant.model.split>) Class.forName(spltstr);
//		System.out.println(c);
//		System.out.println(spltstr);
		System.out.println(splting);
		split splt=new split();
//		Gson gson = new Gson();
//		JsonParser parser = new JsonParser();
//		JsonObject object = (JsonObject) parser.parse(splting.getSplt());
//		HashMap<String, Integer> splitmap = (HashMap<String, Integer>) Arrays.asList(splting.getSplt().split(",")).stream().map(s -> s.split(":")).collect(Collectors.toMap(e -> e[0], e -> Integer.parseInt(e[1])));
//		split splt=gson.fromJson(object,split.class);
//		Map<String,Object> splitmap =
//		        new ObjectMapper().readValue(splting.getSplt(), HashMap.class);
		StringBuilder sb = new StringBuilder(splting.getSplt());
		String st=sb.substring(1,sb.length()-1);
		System.out.println(st);
		Map splitmap = new HashMap();
		System.out.println("------------------------------");
		 String[] elements = st.split(",");
		 for(String s1: elements) {
		     String[] keyValue = s1.split("=");
		     splitmap.put(keyValue[0], keyValue[1]);
		 }
		System.out.println(splitmap);
		boolean status=false;
		if(splitmap.containsKey("split1")) {
			model.addAttribute("amout",splitmap.get("split1") );
			splitmap.remove("split1");
			status=true;
		}
		if(splitmap.containsKey("split2")) {
			model.addAttribute("amout", splitmap.get("split2"));
			splitmap.remove("split2");
			status=true;
		}
		if(splitmap.containsKey("split3")) {
			model.addAttribute("amout", splitmap.get("split3"));
			splitmap.remove("split3");
			status=true;
		}
		if(splitmap.containsKey("split4")) {
			model.addAttribute("amout", splitmap.get("split4"));
			splitmap.remove("split4");
			status=true;
		}
		if(splitmap.containsKey("split5")) {
			model.addAttribute("amout", splitmap.get("split5"));
			splitmap.remove("split5");
			status=true;
		}
		model.addAttribute("split", splt);

		if(status)
		{
			
			model.addAttribute("splitmap",splitmap);
			model.addAttribute("splt",splt);
		return "payment";
		}
		else
			return "redirect:/waiter/finalpayment";
	}

	@RequestMapping("finalpayment")
	public String payment1( Model model, HttpServletRequest req, HttpSession session) {

//		payment.setCustomer_id(new ObjectId(session.getAttribute("custid").toString()));
//		payment.setOrder_id(new ObjectId(session.getAttribute("order_id").toString()));
//		System.out.println(payment);
//		iPaymentRepo.save(payment);
		ReservedTables reservetab = iReservedTablesRepo.findByCustomerid(new ObjectId(session.getAttribute("custid").toString()));
		System.out.println(reservetab);
		reservetab.setStatus("served");
		iReservedTablesRepo.save(reservetab);
		Tables tab = iTableRepo.findBy_id(reservetab.getReserved_table_id());
		tab.setStatus("Not Occupied");
		iTableRepo.save(tab);
		return "redirect:/waiter/home";
	}

	@GetMapping("/logout")
	public String waiterLogout(Model model, HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/home";
	}

}
