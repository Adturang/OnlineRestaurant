package com.onlineRst.onlineRestaurant;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Jclasses.DBRepository;
import com.onlineRst.onlineRestaurant.dao.AllItemRepository;
import com.onlineRst.onlineRestaurant.dao.ContinentalRepository;
import com.onlineRst.onlineRestaurant.dao.FeedbackRepo;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.dao.NonVegetarianRepository;
import com.onlineRst.onlineRestaurant.dao.ProceedOrderRepository;
import com.onlineRst.onlineRestaurant.dao.RateNReviewRepo;
import com.onlineRst.onlineRestaurant.dao.RegistrationRepository;
import com.onlineRst.onlineRestaurant.dao.VegeterainRepository;
import com.onlineRst.onlineRestaurant.model.AlltemAdded;
import com.onlineRst.onlineRestaurant.model.Continental;
import com.onlineRst.onlineRestaurant.model.Feedback;
import com.onlineRst.onlineRestaurant.model.Item;
import com.onlineRst.onlineRestaurant.model.NonVegetarian;
import com.onlineRst.onlineRestaurant.model.OwnRecipe;
import com.onlineRst.onlineRestaurant.model.ProceedOrder;
import com.onlineRst.onlineRestaurant.model.RateNReview;
import com.onlineRst.onlineRestaurant.model.Registration;
import com.onlineRst.onlineRestaurant.model.Vegeterian;
import com.onlineRst.onlineRestaurant.service.AllItemService;
import com.onlineRst.onlineRestaurant.service.ContinentalService;
import com.onlineRst.onlineRestaurant.service.NonVegetarianService;
import com.onlineRst.onlineRestaurant.service.ProceedOrderService;
import com.onlineRst.onlineRestaurant.service.VegetarianService;


@Controller
public class RestaurantController {
	@Autowired
	ContinentalService continentalService; 
	@Autowired
	ContinentalRepository continentalRepository;
	@Autowired
	VegetarianService vegetarianService;
	@Autowired
	VegeterainRepository vegeterainRepository;
	@Autowired
	NonVegetarian nonVegetarian;
	@Autowired
	NonVegetarianRepository nonVegetarianRepository;
	@Autowired
	NonVegetarianService nonVegetarianService;
	@Autowired
	AllItemRepository allItemRepository;
	@Autowired
	AlltemAdded added;
	@Autowired
	AllItemService allItemService;
	@Autowired
	ItemsRepository itemsRepository;
	@Autowired
	ProceedOrder proceedOrder;
	@Autowired
	ProceedOrderRepository orderRepository;
	@Autowired
	ProceedOrderService proceedOrderService;
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
	
	@GetMapping("/adminBhau")
	public String admin() {
		//model.addAttribute("title",title);
		return "ui/adminBhau";
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
		return "ui/ind";
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
	
	@RequestMapping("/destroy")
	public String destroySession(HttpSession request) {
		System.out.println("inside destroy");
		request.invalidate();
		return "redirect:/";
	}
	@RequestMapping("/veg")
	public String veg() {
		recentMapping="veg";
		return "ui/Veg";
	}
	@RequestMapping("/nonVeg")
	public String nonVeg() {
		recentMapping="nonVeg";
		return "ui/non_veg";
	}
	@RequestMapping("/juices")
	public String drinks(HttpSession session, Model model) {
		if(session.getAttribute("msg")!=null) {
			String ss=(String) session.getAttribute("msg");
			model.addAttribute("MSG", ss);
			session.removeAttribute("msg");
		}
		recentMapping="juices";
		return "ui/juices";
	}
	@RequestMapping("/bakery")
	public String bakery() {
		recentMapping="bakery";
		return "ui/bakery";
	}
	@RequestMapping("/continental")
	public String continental() {
		recentMapping="continental";
		return "ui/continental";
	}
	@RequestMapping("/italian")
	public String italian() {
		recentMapping="italian";
		return "ui/italian";
	}
	@RequestMapping("/cart")
	public String cart(Model model) {
		long totalCost=0;
		if (itemList.isEmpty()) {
			model.addAttribute("toCost",totalCost);
		}else {
			for (Item item : itemList) {
				totalCost+=item.getTotalPrice();
			}
			model.addAttribute("toCost",totalCost);
		}
		model.addAttribute("ilist",itemList);
		return "ui/cart";
	}
	@RequestMapping("/addToCart")
	public String addToCart(Item item,HttpSession session,Model model) {
		if (itemList.isEmpty()||!(new DataFinder().isPresent(itemList, item.getName()))) {
			item.setUser(session.getAttribute("sesName").toString());
			item.setType(recentMapping);// hatao in add to own
			Date date = new Date();
			SimpleDateFormat calendar = new SimpleDateFormat("yyyy-MM-dd");
			String d = calendar.format(date);
			item.setDate(d);
			item.setTotalPrice(item.calculateTotalPrice());
			//change add status and time
			item.setStatus("Yet to Confirm");
			irepo.save(item);
			//itemList.add(item);
			System.out.println(item);
			//System.out.println(itemList.size());
		}else {
			session.setAttribute("msg",recentMapping+" "+item.getName()+" Already Added To Your Cart From"+recentMapping);
		}
		return "redirect:/"+recentMapping;
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute("SpringWeb")Item item) {
		System.out.println(item);
		itemList.remove(item);
		return "redirect:/"+item.type;
	}
	@RequestMapping("/cancel")
	public String cancel(@ModelAttribute("SpringWeb")Item item) {
		System.out.println(item);
		itemList.remove(item);
		return "redirect:/"+"cart";
	}
	
	@RequestMapping("/ordered")
	public String ordered() {
		irepo.saveAll(itemList);
		return "redirect:/"+recentMapping;
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
	
	
	// By Gaurav mahi
	@GetMapping("/showAlluser")
	public String showAllUser(HttpSession session) {
		List<Registration> res1 = repo.findAllActiveUsers();

		sortedByName(res1);
		System.out.println(res1);

		System.out.println("inside there" + itemList);
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
		List itemlists = irepo.findAllItem();
		itemlists.forEach(it -> {
			System.out.println("collection in item " + it);
		});
		m.addAttribute("itemList", itemlists);
		return "ui/allItems";
	}
	
	@GetMapping("/top3ItemSelled")
	public String show3mostSelled(Model model) {
		model.addAttribute("itemList",new DBRepository().nameNCounts());
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
		recentMapping = type;
		System.out.println("checking " + recentMapping);
		if (type.equals("Veg")) {

			vegetarianService.saveProductToDB(file, name, price, id, type);
			allItemService.saveProductToDB(file, name, price, id, type);

			// model.addAttribute("itry", itr);

		} else if (type.equals("Non-Veg")) {
			System.out.println("Indisde non veg");

			nonVegetarianService.saveProductToDB(file, name, price, id, recentMapping);
			allItemService.saveProductToDB(file, name, price, id, type);
		} else if (type.equals("continental")) {
			System.out.println("Inside Chinese");
			continentalService.saveProductToDB(file, name, price, id, recentMapping);
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
	public String deleteByName(@RequestParam("name")String name,@RequestParam("type")String type,Model model) {
		if(type.equals("Non-Veg")) {
			nonVegetarianRepository.deleteByName(name);
			Iterable<NonVegetarian> itr = nonVegetarianRepository.findAll();
			model.addAttribute("list", itr);
		}
		if(type.equals("Veg")) {
			System.out.println("inside veg");
			vegeterainRepository.deleteByName(name);
			Iterable<Vegeterian> itr = vegeterainRepository.findAll();
			model.addAttribute("list", itr);
		}
		if(type.equals("continental")) {
			continentalRepository.deleteByName(name);
			Iterable<Continental> itr = continentalRepository.findAll();
			model.addAttribute("list", itr);
		}
		allItemRepository.deleteByName(name);
		
		return "ui/showAllitemsAdded";
	
	}
		
	@RequestMapping("/delete")
	public String proceed(HttpServletRequest httpServletRequest) {

		String type=httpServletRequest.getParameter("fod");
		String itemSelected=httpServletRequest.getParameter("selectedItem");
		System.out.println("Printing   "+type);
		System.out.println("selcted item  "+itemSelected);
		if(type.equals("Non-Veg")) {
			
			nonVegetarianRepository.deleteByName(itemSelected);
			allItemRepository.deleteByName(itemSelected);
			
		}
		if(type.equals("Veg")) {
			System.out.println("inside veg");
			
			vegeterainRepository.deleteByName(itemSelected);
			allItemRepository.deleteByName(itemSelected);
			
		}
		if(type.equals("continental")) {
		
			continentalRepository.deleteByName(itemSelected);
			allItemRepository.deleteByName(itemSelected);
			
		}
		return "ui/demo";
	
	}
	//******************************
	//*********By Divyansh
	//List<OwnRecipe> recList = new ArrayList<OwnRecipe>();
	//List<RateNReview> nReviewList=new ArrayList<RateNReview>();
	List<Item> dtanduser =new ArrayList<Item>();
	@Autowired
	RateNReviewRepo rNReviewRepo; 
		@RequestMapping("/findByDate")			// added new method By DD
		public String rateNreview() {
			return "ui/findByDate";
		}
		
		@RequestMapping("/findDate")// added new method By DD
		public String findDate(@RequestParam("date") String date,Item item,Model model,HttpSession session) {

			System.out.println(date);
			System.out.println("item Odate..."+item.getDate());
			System.out.println("userName " + session.getAttribute("sesName").toString());
			System.out.println(irepo.findByDateAndUser(date,session.getAttribute("sesName").toString()));
			dtanduser=irepo.findByDateAndUser(date,session.getAttribute("sesName").toString());
			model.addAttribute("itemList",irepo.findByDateAndUser(date,session.getAttribute("sesName").toString()));
			return "ui/rateNreview";
		}
		
		@RequestMapping("rv")
		public String rv(Model model) {
			model.addAttribute("itemList",dtanduser);
			return "ui/rateNreview";
		}
		
		@RequestMapping("/rateNreview")					//added new by dd
		public String rateNReview(RateNReview rateNReview,HttpSession session,Item item) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			System.out.println(formatter.format(date));
			rateNReview.setCurDate(formatter.format(date));
			rateNReview.setName(item.getName());
			rateNReview.setUser(session.getAttribute("sesName").toString());
			rateNReview.setODate(item.getDate());
//			nReviewList.add(rateNReview);
			rNReviewRepo.save(rateNReview);	
			System.out.println("Befor delition"+dtanduser.size()+""+dtanduser);
			//deleteFromList(item.name);
			System.out.println("After delition"+dtanduser.size()+""+dtanduser);
			return "redirect:/rv";
		}
		
		public void deleteFromList(String name) {
			if (dtanduser!=null) {
			for (Item item : dtanduser) {
				if (item.name.equals(name)) {
					dtanduser.remove(item);
				}
			}
		}
		}
		
		List<OwnRecipe> recList=new ArrayList<OwnRecipe>();
		@RequestMapping("/addOwnRecipe") // added new method By DD
		public String addOwnRecipe(OwnRecipe recipe, Item item, HttpSession session, Model model) {
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
				//set time and status
				item.setStatus("Yes to Confirm");
				recipe.setStatus("Yes to Confirm");
				//recList.add(recipe);
				//itemList.add(item);
				irepo.save(item);
//				ownRepo.save(recipe);
				System.out.println(recipe);
				System.out.println(item);
				System.out.println(itemList.size());
				System.out.println(recList.size());
			} else {
				session.setAttribute("msg", recipe.getName() + " Already Added To Your Cart");
			}
			return "redirect:/" + "cart";
		}
		
		@Autowired
		FeedbackRepo feedbackRepo;
		@RequestMapping("/submitFeedback")
		public String submitFeedback(HttpSession session,Feedback feedback,Model model) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			feedback.setDate(formatter.format(date));
			feedback.setUser(session.getAttribute("sesName").toString());
			System.out.println(feedback.toString());
			feedbackRepo.save(feedback);
			return "redirect:/i";
		}
	//***********************************************
	
	  
}
