package com.onlineRst.onlineRestaurant.model;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import com.onlineRst.onlineRestaurant.dao.ItemConfirmedRepository;



public class TimeDeleterConfirmed extends TimerTask {
	
	@Autowired
	MailService mailService;
	@Autowired
	ItemConfirmedRepository irepo;
	int id;

	
	public TimeDeleterConfirmed(ItemConfirmedRepository irepo2, int id2) {
		// TODO Auto-generated constructor stub
		this.irepo=irepo2;
		this.id=id2;
	}

	@Override
	public void run() {
		System.out.println("timer Called *****************");
		ItemConfirmed item =irepo.findById(id);
		if (item!=null) {
			irepo.delete(item);

		}
	}
	
	
}
