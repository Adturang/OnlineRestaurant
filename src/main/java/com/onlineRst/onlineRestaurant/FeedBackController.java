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
import com.onlineRst.onlineRestaurant.dao.HistoryRepository;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.dao.RateNReviewRepo;
import com.onlineRst.onlineRestaurant.model.Feedback;
import com.onlineRst.onlineRestaurant.model.History;
import com.onlineRst.onlineRestaurant.model.RateNReview;

@Controller
public class FeedBackController {

	// ******************************
		// *********By Divyansh
		List<History> dtanduser = new ArrayList<History>();
		@Autowired
		RateNReviewRepo rNReviewRepo;
		@Autowired
		ItemsRepository irepo;
		@Autowired
		HistoryRepository historyRepository;

		@RequestMapping("/findByDate") // added new method By DD
		public String rateNreview(HttpSession session) {
			if (session.getAttribute("sesName")==null) {
				return "ui/login";
			}
			return "ui/findByDate";
		}

		@RequestMapping("/findDate") // added new method By DD
		public String findDate(@RequestParam("date") String date, Model model, HttpSession session) {
			if (session.getAttribute("sesName")==null) {
				return "ui/login";
			}
			dtanduser = historyRepository.findByDateAndUser(date, session.getAttribute("sesName").toString());
			model.addAttribute("itemList",dtanduser);
			System.out.println(dtanduser+"***************");
			System.out.println(historyRepository.findAll());
			return "ui/rateNreview";
		}
	
		@RequestMapping("/rateNreview") // added new by dd
		public String rateNReview(RateNReview rateNReview, HttpSession session, History item,Model model) {
			if (session.getAttribute("sesName")==null) {
				return "ui/login";
			}
			dtanduser.remove(item);
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
			model.addAttribute("itemList",dtanduser);
			 return "ui/rateNreview";
		}


		@Autowired
		FeedbackRepo feedbackRepo;

		@RequestMapping("/submitFeedback")
		public String submitFeedback(HttpSession session, Feedback feedback, Model model) {
			if (session.getAttribute("sesName")==null) {
				return "ui/login";
			}
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			feedback.setDate(formatter.format(date));
			feedback.setUser(session.getAttribute("sesName").toString());
			System.out.println(feedback.toString());
			feedbackRepo.save(feedback);
			return "redirect:/i";
		}

}
