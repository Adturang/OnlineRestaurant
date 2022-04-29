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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.onlineRst.onlineRestaurant.model.History;
import com.onlineRst.onlineRestaurant.model.Item;
import com.onlineRst.onlineRestaurant.model.ItemConfirmed;
import com.onlineRst.onlineRestaurant.model.Mail;
import com.onlineRst.onlineRestaurant.model.MailService;
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
	Vegeterian vegeterian;
	@Autowired
	NonVegetarian nonVegetarian;
	@Autowired
	Continental continental;
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
	@Autowired
	History history;

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
		List<History> listOfItem=historyRepository.getItemByType(typeOfItem);
		model.addAttribute("title", item.type);
		
		System.out.println("hiii");
		model.addAttribute("itemList", listOfItem);
		return "ui/allItems";
	}

	@RequestMapping("/showAllitemsWehave")
	public String showAllItems(Model model) {

		Iterable<History> itemlists = historyRepository.findAll();
		model.addAttribute("title", "AllItem");
		model.addAttribute("itemList", itemlists);
		return "ui/allItems";
	}

	@GetMapping("/addItem")
	public String addItem(@RequestParam("type") String type, Model model) {
		System.out.println("anything in the " + type);
		if (type.equals("continental")) {
			model.addAttribute("itemList", continentalRepository.findAll());
			model.addAttribute("object", type);
		}
		if (type.equals("Veg")) {
			model.addAttribute("itemList", vegeterainRepository.findAll());
			model.addAttribute("object", type);
		}
		if (type.equals("Non-Veg")) {
			model.addAttribute("itemList", nonVegetarianRepository.findAll());
			model.addAttribute("object", type);
		}
		return "ui/showItems";

	}

	@GetMapping("/addItemInTheCategotry/{type}")
	public String addItemInCategory(@PathVariable(value = "type") String type, Model model) {
		System.out.println("checkint type  " + type);
		if (type.equals("Veg")) {
			System.out.println("inside vegu");
			model.addAttribute("itemList", vegeterainRepository.findAll());
			model.addAttribute("veg", new Vegeterian());
			model.addAttribute("object", type);
		}
		if (type.equals("Non-Veg")) {
			model.addAttribute("itemList", nonVegetarianRepository.findAll());
			model.addAttribute("veg", new NonVegetarian());
			model.addAttribute("object", type);
		}
		if (type.equals("continental")) {
			model.addAttribute("itemList", continentalRepository.findAll());
			model.addAttribute("veg", new Continental());
			model.addAttribute("object", type);
		}
		return "ui/addItemAdmen";
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
		System.out.println(date);
		List<History> histList = historyRepository.findByDate(date);
		histList.forEach(mms -> {
			History h = mms;
			System.out.println("checking ==========>" + h);
		});

		model.addAttribute("itemList", histList);
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
		List<History> list = historyRepository.findAllWithInRange(initialDate, finalDate);
		list.forEach(t -> {
			System.out.println("checkin result" + t);
		});
		model.addAttribute("itemList", list);
		return "ui/allItems";
	}

	@RequestMapping("/addItemasPerCategory")
	public String addItemAsperCategory(Model model, @RequestParam("file") MultipartFile file,
			@RequestParam("name") String name, @RequestParam("price") int price, String type) {
		if (type.equals("Veg")) {

			vegetarianService.saveProductToDB(file, name, price, type);
			allItemService.saveProductToDB(file, name, price, type);
			model.addAttribute("object", vegeterian);
			// model.addAttribute("itry", itr);

		} else if (type.equals("Non-Veg")) {
			System.out.println("Indisde non veg");

			nonVegetarianService.saveProductToDB(file, name, price, type);
			allItemService.saveProductToDB(file, name, price, type);
			model.addAttribute("object", nonVegetarian);
		} else if (type.equals("continental")) {
			System.out.println("Inside Chinese");
			continentalService.saveProductToDB(file, name, price, type);
			allItemService.saveProductToDB(file, name, price, type);
			model.addAttribute("object", continental);
		}

		return "redirect:/showItemByCategory/" + type;
		// @RequestMapping(value = "/showUser/{userId}", method = RequestMethod.GET)
		// public String showUser(@PathVariable("userId") String userId, Model model)
	}

	@RequestMapping("/showItemByCategory")
	public String showItemByCategory(HttpServletRequest req, Model model, @RequestParam("type") String type) {
		System.out.println("hii");
		// String type = req.getParameter("choice");
		System.out.println("jhbjkb," + type);
		if (type.equals("Veg")) {
			Iterable<Vegeterian> lit = vegeterainRepository.findAll();
			model.addAttribute("object", type);
			model.addAttribute("itemList", lit);
		} else if (type.equals("Non-Veg")) {

			Iterable<NonVegetarian> lit = nonVegetarianRepository.findAll();
			model.addAttribute("itemList", lit);
			model.addAttribute("object", type);
		} else if (type.equals("continental")) {
			Iterable<Continental> lit = continentalRepository.findAll();
			model.addAttribute("itemList", lit);
			model.addAttribute("object", type);
		}
		return "ui/showItems";

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
		System.out.println("hi----------------------------");
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

		return "ui/showItems";

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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(formatter.format(date));

		ic.setDate(formatter.format(date));
		ic.setStatus("Deliverd");

		historyService.saveProductToDB(ic.getId(), ic.getDate(), ic.getName(), ic.getPrice(), ic.getType(), ic.getQty(),
										ic.getTotalPrice(), ic.getStatus(), ic.getTime(), ic.getUser());

		confirmedRepository.deleteById(id0);
		//Email Part
		Mail mail = new Mail();
        mail.setMailFrom("adturang@gmail.com");
        String mailAddr=regRepo.getRegistrationByUsername(ic.getUser()).getEmail();
        System.out.println(mail+" xoxoxoxoxox  "+mailAddr);
        mail.setMailTo(mailAddr);
        mail.setMailSubject("Your "+ic.getName()+" Order Delived- Confirmed");
        mail.setMailContent("Your order "+ic.getName()+" is Deliverd..Thank you for visiting");		       
        mailService.sendEmail(mail);
		return  "redirect:/delivery";
	}
	@Autowired
	MailService mailService;
	@Autowired
	RegistrationRepository regRepo;

	@RequestMapping("/admin")
	public String admin() {
		return "ui/adminBhau";
	}

	@RequestMapping("/saveItem")
	public String saveToRepo(Model model, @RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("price") int price, @RequestParam("type") String type) {
		allItemService.saveProductToDB(file, name, price, type);
		System.out.println("checking type is a good idea" + type);
		if (type.equals("Veg")) {
			vegetarianService.saveProductToDB(file, name, price, type);
			model.addAttribute("itemList", vegeterainRepository.findAll());
			model.addAttribute("object", type);
		}
		if (type.equals("Non-Veg")) {
			nonVegetarianService.saveProductToDB(file, name, price, type);
			model.addAttribute("itemList", nonVegetarianRepository.findAll());
			model.addAttribute("object", type);
		}
		if (type.equals("continental")) {
			System.out.println("inside continental------------");
			continentalService.saveProductToDB(file, name, price, type);
			model.addAttribute("itemList", continentalRepository.findAll());
			model.addAttribute("object", type);
		}
		model.addAttribute("msg1","Added Successfully");
		return "ui/AddedSucs";
	}

	@GetMapping("/deleteByName/{name}/{type}")
	public String multiplePathVariable(@PathVariable("type") String type, @PathVariable("name") String name,
			Model model) {
		System.out.println(name + "----------------------->" + type);
		allItemRepository.deleteByName(name);
		if (type.equals("Veg")) {
			vegeterainRepository.deleteByName(name);
			model.addAttribute("itemList", vegeterainRepository.findAll());
			model.addAttribute("object", type);

		}
		if (type.equals("Non-Veg")) {
			nonVegetarianRepository.deleteByName(name);
			model.addAttribute("itemList", nonVegetarianRepository.findAll());
			model.addAttribute("object", type);

		}
		if (type.equals("continental")) {
			continentalRepository.deleteByName(name);
			model.addAttribute("itemList", continentalRepository.findAll());
			model.addAttribute("object", type);

		}
		System.out.println(name + "    " + type);
		model.addAttribute("msg1","Deleted Success");
		return "ui/AddedSucs";
	}

	@GetMapping("/updateByName/{name}/{type}")
	public String updateByName(@PathVariable("type") String type, @PathVariable("name") String name, Model model) {
		if (type.equals("Veg")) {
			Vegeterian vegeterian = vegeterainRepository.getItemByName(name);

			System.out.println("veg***************" + vegeterian);
			model.addAttribute("obj", vegeterian);
		}

		if (type.equals("Non-Veg")) {
			NonVegetarian nonvegeterian = nonVegetarianRepository.getItemByName(name);
             System.out.println("inside no ---------------------");
			model.addAttribute("obj", nonvegeterian);
		}
		if (type.equals("continental")) {
			Continental cont = continentalRepository.getItemByName(name);

			System.out.println("veg***************" + vegeterian);
			model.addAttribute("obj", cont);
		}
		System.out.println(name + "----------------------->" + type);

		return "ui/update";
	}

	@RequestMapping("/d")
	public String demo(@RequestParam("type") String type, Model model) {
		System.out.println("Checkin all" + type);
		if (type.equals("Veg")) {

			model.addAttribute("itemList", vegeterainRepository.findAll());
			model.addAttribute("object", type);
		}
		if (type.equals("Non-Veg")) {

			model.addAttribute("itemList", nonVegetarianRepository.findAll());
			model.addAttribute("object", type);
		}
		if (type.equals("continental")) {
			System.out.println("inside continental------------");

			model.addAttribute("itemList", continentalRepository.findAll());
			model.addAttribute("object", type);
		}

		return "ui/showItems";
	}

	@RequestMapping("/move")
	public String backToad() {

		return "ui/adminBhau";
	}

	@RequestMapping("/updateItem")
	public String update(HttpServletRequest req, Model model, @RequestParam("id") int id,
			@RequestParam("type") String type) {
		System.out.println("Checking " + type + "   " + id);

		String price = req.getParameter("price");

		System.out.println(price);
		if (type.equals("Veg")) {
			System.out.println("inside veg");
			vegeterainRepository.updateMethod(Integer.parseInt(price), id);
			model.addAttribute("itemList", vegeterainRepository.findAll());

		}
		if (type.equals("Non-Veg")) {

			nonVegetarianRepository.updateMethod(Integer.parseInt(price), id);
			model.addAttribute("itemList", nonVegetarianRepository.findAll());
		}
		if (type.equals("continental")) {

			continentalRepository.updateMethod(Integer.parseInt(price), id);
			model.addAttribute("itemList", continentalRepository.findAll());

		}
		allItemRepository.updateMethod(Integer.parseInt(price), id);
		model.addAttribute("msg1","Updated Successfully");
		return "ui/AdminBhau";
	}

	@RequestMapping("/back")
	public String back() {
		return "ui/adminBhau";
	}
}
