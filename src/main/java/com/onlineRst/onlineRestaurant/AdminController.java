package com.onlineRst.onlineRestaurant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.Jclasses.DBRepository;
import com.onlineRst.onlineRestaurant.dao.AllItemRepository;
import com.onlineRst.onlineRestaurant.dao.ContinentalRepository;
import com.onlineRst.onlineRestaurant.dao.HistoryRepository;
import com.onlineRst.onlineRestaurant.dao.ItemConfirmedRepository;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.dao.NonVegetarianRepository;
import com.onlineRst.onlineRestaurant.dao.RegistrationRepository;
import com.onlineRst.onlineRestaurant.dao.VegeterainRepository;
import com.onlineRst.onlineRestaurant.model.Continental;
import com.onlineRst.onlineRestaurant.model.Item;
import com.onlineRst.onlineRestaurant.model.ItemConfirmed;
import com.onlineRst.onlineRestaurant.model.NonVegetarian;
import com.onlineRst.onlineRestaurant.model.Registration;
import com.onlineRst.onlineRestaurant.model.Vegeterian;
import com.onlineRst.onlineRestaurant.service.AllItemService;
import com.onlineRst.onlineRestaurant.service.ContinentalService;
import com.onlineRst.onlineRestaurant.service.HistoryService;
import com.onlineRst.onlineRestaurant.service.NonVegetarianService;
import com.onlineRst.onlineRestaurant.service.VegetarianService;

@Controller
public class AdminController {

	@Autowired
	ContinentalService continentalService;
	@Autowired
	ContinentalRepository continentalRepository;
	@Autowired
	VegetarianService vegetarianService;
	@Autowired
	VegeterainRepository vegeterainRepository;
	@Autowired
	NonVegetarianRepository nonVegetarianRepository;
	@Autowired
	NonVegetarianService nonVegetarianService;
	@Autowired
	AllItemRepository allItemRepository;
	@Autowired
	AllItemService allItemService;
	@Autowired
	RegistrationRepository repo;//
	@Autowired
	ItemsRepository irepo;//
	@Autowired
	HistoryRepository historyRepository;
	@Autowired
	HistoryService historyService;
	@Autowired
	ItemConfirmedRepository confirmedRepository;

	// By Gaurav mahi
	@GetMapping("/showAlluser")
	public String showAllUser(HttpSession session) {
		List<Registration> res1 = repo.findAllActiveUsers();
		sortedByName(res1);
		System.out.println(res1);
		session.setAttribute("alluser", res1);
		return "ui/allUser";
	}

	private void sortedByName(List<Registration> res1) {
		res1.sort((o1, o2) -> o1.getUserName().toLowerCase().compareTo(o2.getUserName().toLowerCase()));

	}

	@RequestMapping("/showAllitems")
	public String showAllItems(Model model, Item item) {
		System.out.println("hii im insidfe");
		String typeOfItem = item.getType();
		System.out.println("the type is " + typeOfItem);
		List<Item> listOfItem = irepo.getItemByType(typeOfItem);
		listOfItem.forEach(itemAny -> {
			System.out.println(itemAny);
		});
		System.out.println("hiii");
		model.addAttribute("itemList", listOfItem);
		return "ui/allItems";
	}

	@RequestMapping("/showAllitemsWehave")
	public String showAllItems(Model m) {

		List<Item> itemlists = irepo.findAllItem();//

		m.addAttribute("itemList", itemlists);
		return "ui/allItems";
	}

	@GetMapping("/top3ItemSelled")
	public String show3mostSelled(Model model) {
		model.addAttribute("itemList", new DBRepository().nameNCounts());
		return "ui/top3";
	}

	@RequestMapping("/showWithInSpecifieddate")
	public String dateTodo(Model model, HttpServletRequest req, Item itm) {
		String date = req.getParameter("dat");
		System.out.println("Checking date " + date);
		List<Item> listt = irepo.findAllWithCreationDateTimeBefore(date);
		listt.forEach(t -> {
			System.out.println("checkin result" + t);
		});
		model.addAttribute("itemList", listt);
		System.out.println("checking " + itm.calculateTotalPrice());
		model.addAttribute("any", itm.calculateTotalPrice());

		return "ui/allItems";
	}

	@RequestMapping("/showWithInSpecifiedRangeOfDate")
	public String datewithSpecifiedrange(Model model, HttpServletRequest req) {

		String initialDate = req.getParameter("initialDate");
		String finalDate = req.getParameter("finalDate");

		System.out.println("Checking initial date " + initialDate);
		System.out.println("FinalDate" + finalDate);
		List<Item> list = irepo.findAllWithInRange(initialDate, finalDate);
		list.forEach(t -> {
			System.out.println("checkin result" + t);
		});
		model.addAttribute("itemList", list);
		return "ui/allItems";
	}

	@RequestMapping("/addItemasPerCategory")
	public String addItemAsperCategory(Model model, @RequestParam("file") MultipartFile file,
			@RequestParam("name") String name, @RequestParam("id") int id, @RequestParam("price") int price,
			String type) {
		if (type.equals("Veg")) {

			vegetarianService.saveProductToDB(file, name, price, id, type);
			allItemService.saveProductToDB(file, name, price, id, type);

			// model.addAttribute("itry", itr);

		} else if (type.equals("Non-Veg")) {
			System.out.println("Indisde non veg");

			nonVegetarianService.saveProductToDB(file, name, price, id, type);
			allItemService.saveProductToDB(file, name, price, id, type);
		} else if (type.equals("continental")) {
			System.out.println("Inside Chinese");
			continentalService.saveProductToDB(file, name, price, id, type);
			allItemService.saveProductToDB(file, name, price, id, type);
		}

		return "redirect:/adminBhau";

	}

	@RequestMapping("/showItemByCategory")
	public String showItemByCategory(HttpServletRequest req, Model model) {
		System.out.println("hii");
		String type = req.getParameter("choice");
		System.out.println("jhbjkb," + type);
		if (type.equals("Veg")) {
			Iterable<Vegeterian> lit = vegeterainRepository.findAll();
			lit.forEach(item -> {
				System.out.println("item in the " + item);
			});
			model.addAttribute("list", lit);
		} else if (type.equals("Non-Veg")) {

			Iterable<NonVegetarian> lit = nonVegetarianRepository.findAll();
			model.addAttribute("list", lit);
		} else if (type.equals("continental")) {
			Iterable<Continental> lit = continentalRepository.findAll();
			model.addAttribute("list", lit);
		}
		return "ui/showAllitemsAdded";

	}

	@RequestMapping("/deleteItemByCategory")
	public String deleteItemByCategory(Model model, HttpServletRequest req) {
		String type = req.getParameter("choice");
		System.out.println("checking type " + type);

		if (type.equals("Veg")) {
			Iterable<Vegeterian> itr = vegeterainRepository.findAll();
			itr.forEach(l -> {
				System.out.println("checking    " + l);
			});
			model.addAttribute("vegList", itr);

		} else if (type.equals("Non-Veg")) {
			System.out.println("Indisde non veg");
			Iterable<NonVegetarian> itr = nonVegetarianRepository.findAll();
			model.addAttribute("vegList", itr);

		} else if (type.equals("continental")) {
			System.out.println("Inside Continetal");
			// System.out.println("Indisde non veg");
			Iterable<Continental> itr = continentalRepository.findAll();
			model.addAttribute("vegList", itr);
		}

		// model.addAttribute("list", lit);
		return "ui/itemdeleted";

	}

	@RequestMapping("/deleteByName")
	public String deleteByName(@RequestParam("name") String name, @RequestParam("type") String type, Model model) {
		if (type.equals("Non-Veg")) {
			nonVegetarianRepository.deleteByName(name);
			Iterable<NonVegetarian> itr = nonVegetarianRepository.findAll();
			model.addAttribute("list", itr);
		}
		if (type.equals("Veg")) {
			System.out.println("inside veg");
			vegeterainRepository.deleteByName(name);
			Iterable<Vegeterian> itr = vegeterainRepository.findAll();
			model.addAttribute("list", itr);
		}
		if (type.equals("continental")) {
			continentalRepository.deleteByName(name);
			Iterable<Continental> itr = continentalRepository.findAll();
			model.addAttribute("list", itr);
		}
		allItemRepository.deleteByName(name);

		return "ui/showAllitemsAdded";

	}

	@RequestMapping("/delete")
	public String proceed(HttpServletRequest httpServletRequest) {

		String type = httpServletRequest.getParameter("fod");
		String itemSelected = httpServletRequest.getParameter("selectedItem");
		System.out.println("Printing   " + type);
		System.out.println("selcted item  " + itemSelected);
		if (type.equals("Non-Veg")) {

			nonVegetarianRepository.deleteByName(itemSelected);
			allItemRepository.deleteByName(itemSelected);

		}
		if (type.equals("Veg")) {
			System.out.println("inside veg");

			vegeterainRepository.deleteByName(itemSelected);
			allItemRepository.deleteByName(itemSelected);

		}
		if (type.equals("continental")) {

			continentalRepository.deleteByName(itemSelected);
			allItemRepository.deleteByName(itemSelected);

		}
		return "ui/demo";

	}
	
	@RequestMapping("/historyForAdmin")
	public String checkHistoryForAdmin(Model model, HttpSession session) {
		System.out.println(historyRepository.findAll());
		model.addAttribute("hs", historyRepository.findAll());
		return "ui/demo";
	}

	// ******************************

	@RequestMapping("/ordererdItem")
	public String orderStage(Model m, HttpServletRequest req) {

		Iterable<ItemConfirmed> itemConfirmed = confirmedRepository.findAll();
		itemConfirmed.forEach(o -> {
			System.out.println(o);
		});
		m.addAttribute("it", itemConfirmed);

		return "ui/action";

	}

	@RequestMapping("/ready")
	public String ready(HttpServletRequest req) {
		String action = req.getParameter("action");
		String id = req.getParameter("idd");
		int id0 = Integer.parseInt(id);
		System.out.println(id + "  and the action takern is" + action);
		ItemConfirmed ic = confirmedRepository.findById(id0);
		ic.setStatus(action);
		System.out.println("checking " + ic);
//		//ItemConfirmed added = confirmedRepository.getItemByName(item);
//		added.setStatus(action);
		confirmedRepository.save(ic);
		return "redirect:/ordererdItem";
	}

	@RequestMapping("/delivery")
	public String deliverMethod(Model model) {
		List<ItemConfirmed> ic = new ArrayList<ItemConfirmed>();
		Iterable<ItemConfirmed> itr = confirmedRepository.findAll();
		for (ItemConfirmed con : itr) {
			System.out.println("checkin  " + con);
			if (con.getStatus().equals("Prepared")) {
				ic.add(con);
				System.out.println("condition checked");
				model.addAttribute("actionOrder", ic);
				// confirmedRepository.deleteByName(con.getName());
			}

		}
		return "ui/delivery";
	}

	@RequestMapping("/deliverMe")
	public String deliverMe(HttpServletRequest req, Model model) {
		String id = req.getParameter("id");
		int id0 = Integer.parseInt(id);
		ItemConfirmed ic = confirmedRepository.findById(id0);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String t = sdf.format(cal.getTime());
		ic.setTime(t);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		System.out.println(formatter.format(date));

		ic.setDate(formatter.format(date));
		ic.setStatus("Deliverd");

		historyService.saveProductToDB(ic.getId(), ic.getDate(), ic.getName(), ic.getPrice(), ic.getType(), ic.getQty(),
				ic.getTotalPrice(), ic.getStatus(), ic.getTime(), ic.getUser());

		confirmedRepository.deleteById(id0);
		return  "redirect:/delivery";
	}

}
