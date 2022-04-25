package com.onlineRst.onlineRestaurant.model;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.dao.RegistrationRepository;
import com.onlineRst.onlineRestaurant.service.MailService;


public class TimeDeleter extends TimerTask {
	@Autowired
	RegistrationRepository regRepo;
	@Autowired
	MailService mailService;
	@Autowired
	ItemsRepository irepo;
	int id;

	
	public TimeDeleter(ItemsRepository irepo2, int id2) {
		// TODO Auto-generated constructor stub
		this.irepo=irepo2;
		this.id=id2;
	}

	@Override
	public void run() {
		System.out.println("timer Called *****************");
		Item item =irepo.findById(id);
		if (item!=null) {
			irepo.delete(item);
			
			////email
				Mail mail = new Mail();
		        mail.setMailFrom("adturang@gmail.com");
		        mail.setMailTo(regRepo.getRegistrationByUsername(item.getUser()).getEmail());
		        mail.setMailSubject("Your "+item.getName()+" Order Deleted- Expired");
		        mail.setMailContent("Sorry ,We did not get your confirmation on "+item.getName()+" within time that's why its automatically deleted..Thank you for visiting");		       
		        mailService.sendEmail(mail);
			
			///
		}
	}
	
	
}
