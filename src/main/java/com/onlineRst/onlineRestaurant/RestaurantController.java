package com.onlineRst.onlineRestaurant;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RestaurantController {
	
	@Autowired
	RegistrationRepository repo;
	@Autowired
	ItemsRepository irepo;
	String recentMapping;
	List<Item> itemList=new ArrayList<Item>();
	
	//@Value("${bank.title}")String title;
	@GetMapping("/showRegForm")
	public String registForm(Model model) {
		//model.addAttribute("title",title);
		return "ui/register";
	}
	
	@GetMapping("/showLoginForm")
	public String loginForm(HttpSession session, Model model) {
		//model.addAttribute("title",title);
		if(session.getAttribute("msg")!=null) {
			model.addAttribute("MSG", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
		return "ui/login";
	}
	
	@PostMapping("/auth")
	public String callAuth(@RequestParam("userName")String username,@RequestParam("password")String password,HttpSession session) {

		String res=new DBCheck().isAuthorized(username,password,repo);
		System.out.println(res);
		if (res.equals("invalid")) {
			return "ui/login";
		}else if (res.equals("Admin")) {
			session.setAttribute("sesName",username);
			return "ui/adminBhau";
		}else {
			session.setAttribute("sesName",username);
		return "ui/Index";
		}
	}
	
	@PostMapping("/save")
	public String save(Model model,Registration reg,HttpSession session) {
		System.out.println("HelloAccount");
		;
		if(repo.save(reg)!=null){
			session.setAttribute("msg", "Successfully Registerd.");
		}
		else {
			session.setAttribute("msg", "Somethong Went wrong.!!!");
		}
		return "redirect:/showLoginForm";
	}
	
	@PostMapping("/destroy")
	public String destroySession(HttpSession request) {
		request.invalidate();
		return "redirect:/home";
	}
	@PostMapping("/veg")
	public String veg() {
		recentMapping="veg";
		return "ui/veg";
	}
	@PostMapping("/nonVeg")
	public String nonVeg() {
		recentMapping="nonVeg";
		return "ui/nonVeg";
	}
	@PostMapping("/drinks")
	public String drinks() {
		recentMapping="drinks";
		return "ui/drinks";
	}
	@PostMapping("/addToCart")
	public String addToCart(Item item,HttpSession session) {
		item.setUserName(session.getAttribute("sesName").toString());
		item.setType(recentMapping);
		itemList.add(item);
		return "redirect:/"+recentMapping;
	}
	
	@PostMapping("/ordered")
	public String ordered(List<Item> items) {
		irepo.saveAll(items);
		return "redirect:/home";
	}
	@RequestMapping("/")
	public String index() {
		return "ui/Index";
	}
	
	  
}
