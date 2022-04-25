package com.onlineRst.onlineRestaurant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onlineRst.onlineRestaurant.dao.FeedbackRepo;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.dao.RateNReviewRepo;
import com.onlineRst.onlineRestaurant.model.Feedback;
import com.onlineRst.onlineRestaurant.model.Item;
import com.onlineRst.onlineRestaurant.model.RateNReview;

@Controller
public class FeedBackController {

	// ******************************
		// *********By Divyansh
		List<Item> dtanduser = new ArrayList<Item>();
		@Autowired
		RateNReviewRepo rNReviewRepo;
		@Autowired
		ItemsRepository irepo;

		@RequestMapping("/findByDate") // added new method By DD
		public String rateNreview() {
			return "ui/findByDate";
		}

		@RequestMapping("/findDate") // added new method By DD
		public String findDate(@RequestParam("date") String date, Item item, Model model, HttpSession session) {

			System.out.println(date);
			System.out.println("item Odate..." + item.getDate());
			System.out.println("userName " + session.getAttribute("sesName").toString());
			System.out.println(irepo.findByDateAndUser(date, session.getAttribute("sesName").toString()));
			dtanduser = irepo.findByDateAndUser(date, session.getAttribute("sesName").toString());
			model.addAttribute("itemList", irepo.findByDateAndUser(date, session.getAttribute("sesName").toString()));
			return "ui/rateNreview";
		}

		@RequestMapping("rv")
		public String rv(Model model) {
			model.addAttribute("itemList", dtanduser);
			return "ui/rateNreview";
		}

		@RequestMapping("/rateNreview") // added new by dd
		public String rateNReview(RateNReview rateNReview, HttpSession session, Item item) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			System.out.println(formatter.format(date));
			rateNReview.setCurDate(formatter.format(date));
			rateNReview.setName(item.getName());
			rateNReview.setUser(session.getAttribute("sesName").toString());
			rateNReview.setODate(item.getDate());
//				nReviewList.add(rateNReview);
			rNReviewRepo.save(rateNReview);
			System.out.println("Befor delition" + dtanduser.size() + "" + dtanduser);
			// deleteFromList(item.name);
			System.out.println("After delition" + dtanduser.size() + "" + dtanduser);
			return "redirect:/rv";
		}


		@Autowired
		FeedbackRepo feedbackRepo;

		@RequestMapping("/submitFeedback")
		public String submitFeedback(HttpSession session, Feedback feedback, Model model) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			feedback.setDate(formatter.format(date));
			feedback.setUser(session.getAttribute("sesName").toString());
			System.out.println(feedback.toString());
			feedbackRepo.save(feedback);
			return "redirect:/i";
		}

}
