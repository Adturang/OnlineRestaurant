package com.onlineRst.onlineRestaurant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlineRst.onlineRestaurant.dao.HistoryRepository;
import com.onlineRst.onlineRestaurant.dao.ItemConfirmedRepository;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.dao.OwnRepo;
import com.onlineRst.onlineRestaurant.model.Item;
import com.onlineRst.onlineRestaurant.model.ItemConfirmed;
import com.onlineRst.onlineRestaurant.model.OwnRecipe;
import com.onlineRst.onlineRestaurant.model.TimeDeleterConfirmed;
import com.onlineRst.onlineRestaurant.service.ItemConfirmedService;

@Controller
public class CartController {
	@Autowired
	ItemsRepository irepo;
	@Autowired
	ItemConfirmedRepository confirmedRepository;
	@Autowired
	ItemConfirmedService itemConfirmedService;
	@Autowired
	HistoryRepository historyRepository;

	
	@RequestMapping("/cart")
	public String cart(Model model, HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		List<Item> itemlist = irepo.findAllByuserName(session.getAttribute("sesName").toString());
		long totalCost = 0;
		if (itemlist.isEmpty()) {
			model.addAttribute("toCost", totalCost);
		} else {
			for (Item item : itemlist) {
				totalCost += item.getTotalPrice();
			}

			model.addAttribute("toCost", totalCost);
		}
		model.addAttribute("ilist", itemlist);
		Iterable<ItemConfirmed> g = confirmedRepository.findAllByuserName(session.getAttribute("sesName").toString());
		long totalCostforConfirmed = 0;
		for (ItemConfirmed item : g) {
			totalCostforConfirmed += item.getTotalPrice();
		}
		model.addAttribute("sum", totalCostforConfirmed + totalCost);
		model.addAttribute("confirmtoCost", totalCostforConfirmed);

		model.addAttribute("olist", g);
		return "ui/cart";
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute("SpringWeb") Item item, Model model,HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		model.addAttribute("item", item);

		return "ui/editCart";
	}
	
	@RequestMapping("/setQty")
	public String demoCheckl(Model model, @ModelAttribute("SpringWeb") Item item,HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		int p = item.calculateTotalPrice();
		item.setTotalPrice(p);
		irepo.save(item);
		return "redirect:/" + "cart";
	}
	
	@RequestMapping("/cancel")
	public String cancel(@ModelAttribute("SpringWeb") Item item,HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		System.out.println(item);
		irepo.deleteByNameAndUser(item.getName(), item.getUser());
		// itemList.remove(item);
		return "redirect:/" + "cart";
	}

	@RequestMapping("/cancelConfirmed")
	public String cancelConfirmed(@ModelAttribute("SpringWeb") ItemConfirmed ic,HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		confirmedRepository.deleteByNameAndUser(ic.getName(), ic.getUser());

		// itemList.remove(item);
		return "redirect:/" + "cart";
	}

	@RequestMapping("/cancelAllItems")
	public String cancelAllItems(HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		irepo.deleteByUser(session.getAttribute("sesName").toString());
		// itemList.remove(item);
		return "redirect:/" + "cart";
	}
	@RequestMapping("/cancelAllConfirmedOerder")
	public String cancelAllConfirmedOerder(HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		confirmedRepository.deleteByUser(session.getAttribute("sesName").toString());
		// itemList.remove(item);
		return "redirect:/" + "cart";
	}
	
	
	@RequestMapping("/ordered")
	public String ordered(Item item, Model model, HttpServletRequest req,HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		item.setStatus("confirmed");
		System.out.println(item);
		int op = item.calculateTotalPrice();
		item.setTotalPrice(op);
		itemConfirmedService.saveProductToDB(item.getId(), item.getDate(), item.getName(), item.getPrice(),
		item.getType(), item.getQty(), item.getTotalPrice(), item.getStatus(), item.getTime(), item.getUser());
		String name = req.getParameter("name");
		System.out.println("Checking " + name);
		ItemConfirmed itemConfirmed=itemConfirmedRepository.findByItemAndUser(item.getName(), item.getUser());
		Calendar date1 = Calendar.getInstance();
		long timeInSecs = date1.getTimeInMillis();
		Date afterAdding = new Date(timeInSecs + (1 * 60 * 1000));
		Timer tm = new Timer("timer-"+item.getId());
		tm.schedule(new TimeDeleterConfirmed(itemConfirmedRepository,itemConfirmed.getId()),afterAdding );	
		// irepo.deleteByName(name);
		irepo.deleteByName(item.getName());

		return "redirect:/" + "cart";
	}
	@Autowired
	ItemConfirmedRepository itemConfirmedRepository;
	
	//************ByDivyansh
	
	List<OwnRecipe> recList = new ArrayList<OwnRecipe>();
	@Autowired
	OwnRepo ownRepo; 
	@RequestMapping("/addOwnRecipe") // added new method By DD
	public String addOwnRecipe(OwnRecipe recipe, Item item, HttpSession session, Model model) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		if (recList.isEmpty() || !(new DataFinder().isExist(recList, recipe.getName()))) {
			recipe.setUName(session.getAttribute("sesName").toString());
			item.setUser(session.getAttribute("sesName").toString());
			item.setTotalPrice(item.calculateTotalPrice());
			recipe.setTotalPrice(recipe.calculateTotalPrice());
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			System.out.println(formatter.format(date));
			recipe.setDate(formatter.format(date));
			item.setDate(formatter.format(date));
			// set time and status
			item.setStatus("Yet to Confirm");
			recipe.setStatus("Yet to Confirm");
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String t = sdf.format(cal.getTime());
			item.setTime(t);
			// recList.add(recipe);
			// itemList.add(item);
			irepo.save(item);
			ownRepo.save(recipe);
			System.out.println(recipe);
			System.out.println(item);
			System.out.println(recList.size());
		} else {
			session.setAttribute("msg", recipe.getName() + " Already Added To Your Cart");
		}
		return "redirect:/" + "cart";
	}
	
	@RequestMapping("/history")
	public String checkHistory(Model model, HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		System.out.println(historyRepository.findAll());
		model.addAttribute("hs", historyRepository.findAllByuserName(session.getAttribute("sesName").toString()));
		return "ui/demo";
	}
	
	@RequestMapping("/ownHistory")
	public String ownHistory(Model model, HttpSession session) {
		if (session.getAttribute("sesName")==null) {
			return "ui/login";
		}
		model.addAttribute("oh", ownRepo.findAllByuserName(session.getAttribute("sesName").toString()));
		return "ui/ownHistory";
	}
}
