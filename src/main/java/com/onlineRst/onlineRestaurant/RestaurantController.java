package com.onlineRst.onlineRestaurant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.dao.RegistrationRepository;
import com.onlineRst.onlineRestaurant.model.Item;
import com.onlineRst.onlineRestaurant.model.Registration;
import com.onlineRst.onlineRestaurant.model.TimeDeleter;
import com.onlineRst.onlineRestaurant.service.ItemService;

@Controller
public class RestaurantController {

	@Autowired
	ItemService itemService;
	@Autowired
	ItemsRepository itemsRepository;
	@Autowired
	RegistrationRepository repo;
	@Autowired
	ItemsRepository irepo;
	String recentMapping;
	List<Item> itemList = new ArrayList<Item>();
	List<Item> itemlist;

	// @Value("${bank.title}")String title;
	@GetMapping("/showRegForm")
	public String registForm(Model model) {
		// model.addAttribute("title",title);
		return "ui/register";
	}

	@GetMapping("/adminBhau")
	public String admin() {
		// model.addAttribute("title",title);
		return "ui/adminBhau";
	}

	@GetMapping("/showLoginForm")
	public String loginForm(HttpSession session, Model model) {
		// model.addAttribute("title",title);
		if (session.getAttribute("msg") != null) {
			model.addAttribute("MSG", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
		return "ui/login";
	}

	@PostMapping("/auth")
	public String callAuth(@RequestParam("userName") String username, @RequestParam("password") String password,
			HttpSession session) {

		String res = new DBCheck().isAuthorized(username, password, repo);
		System.out.println(res);
		if (res.equals("invalid")) {
			return "ui/login";
		} else if (res.equals("Admin")) {
			session.setAttribute("sesName", username);
			return "ui/adminBhau";
		} else {
			session.setAttribute("sesName", username);
			return "ui/ind";
		}
	}

	@PostMapping("/save")
	public String save(Model model, Registration reg, HttpSession session) {
		System.out.println("HelloAccount");
		;
		if (repo.save(reg) != null) {
			session.setAttribute("msg", "Successfully Registerd.");
		} else {
			session.setAttribute("msg", "Somethong Went wrong.!!!");
		}
		return "redirect:/showLoginForm";
	}

	@RequestMapping("/destroy")
	public String destroySession(HttpSession request) {
		System.out.println("inside destroy");
		request.invalidate();
		return "redirect:/";
	}

	@RequestMapping("/veg")
	public String veg() {
		recentMapping = "veg";
		return "ui/Veg";
	}

	@RequestMapping("/nonVeg")
	public String nonVeg() {
		recentMapping = "nonVeg";
		return "ui/non_veg";
	}

	@RequestMapping("/juices")
	public String drinks(HttpSession session, Model model) {
		if (session.getAttribute("msg") != null) {
			String ss = (String) session.getAttribute("msg");
			model.addAttribute("MSG", ss);
			session.removeAttribute("msg");
		}
		recentMapping = "juices";
		return "ui/juices";
	}

	@RequestMapping("/bakery")
	public String bakery() {
		recentMapping = "bakery";
		return "ui/bakery";
	}

	@RequestMapping("/continental")
	public String continental() {
		recentMapping = "continental";
		return "ui/continental";
	}

	@RequestMapping("/italian")
	public String italian() {
		recentMapping = "italian";
		return "ui/italian";
	}

	@RequestMapping("/addToCart")
	public String addToCart(Item item, HttpSession session, Model model) {
		if (itemList.isEmpty() || !(new DataFinder().isPresent(itemList, item.getName()))) {
			item.setUser(session.getAttribute("sesName").toString());
			item.setType(recentMapping);// hatao in add to own
			Date date = new Date();
			SimpleDateFormat calendar = new SimpleDateFormat("yyyy-MM-dd");
			String d = calendar.format(date);
			item.setDate(d);
			item.setTotalPrice(item.calculateTotalPrice());
			// change add status and time
			// changes--------------------------
			item.setStatus("Yet to Confirm");
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String t = sdf.format(cal.getTime());
			System.out.println("time is " + t);
			//
			item.setTime(t);

			List<Item> listToCheckDuplicate = irepo.getByItemAndUser(item.getName(), item.getUser());
			if (listToCheckDuplicate.size() == 0) {
				System.out.println("item size " + listToCheckDuplicate.size());
				irepo.save(item);
				Calendar date1 = Calendar.getInstance();
				long timeInSecs = date1.getTimeInMillis();
				Date afterAdding = new Date(timeInSecs + (2 * 60 * 1000));
				Timer tm = new Timer("timer-"+item.getId());
				tm.schedule(new TimeDeleter(irepo,item.getId()),afterAdding );
			}
			// itemList.add(item);
			System.out.println("-----------" + item);
			// System.out.println(itemList.size());
		} else {
			session.setAttribute("msg",
					recentMapping + " " + item.getName() + " Already Added To Your Cart From" + recentMapping);
		}
		return "redirect:/" + recentMapping;
	}
	
	@RequestMapping("/")
	public String index() {
		return "ui/Index";
	}

	@RequestMapping("/i")
	public String calluser() {
		return "ui/ind";
	}

	@PostMapping("/in")
	public String ind() {
		return "ui/ind";
	}
	
		/// gaurav


//	// ***********************************************

}
