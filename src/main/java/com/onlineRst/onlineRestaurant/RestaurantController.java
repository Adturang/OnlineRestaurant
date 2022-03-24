package com.onlineRst.onlineRestaurant;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RestaurantController {

	@Autowired
	RegistrationRepository repo;
	
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
	public String callAuth(@RequestParam("userName")String username,@RequestParam("password")String password) {

		String res=new DBCheck().isAuthorized(username,password,repo);
		System.out.println(res);
		if (res.equals("invalid")) {
			return "ui/login";
		}else if (res.equals("Admin")) {
			return "ui/adminBhau";
		}else {
		return "ui/user";
		}
	}
	
	//@Value("${bank.welcome}")String welmsg;
	@PostMapping("/save")
	public String save(Model model,Registration reg,HttpSession session) {
		//model.addAttribute("welcome",welmsg);
		model.addAttribute("acct",reg);
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
}
