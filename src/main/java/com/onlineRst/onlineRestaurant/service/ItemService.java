package com.onlineRst.onlineRestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.model.Item;

@Service
public class ItemService {
	@Autowired
	private ItemsRepository repository;
	@Autowired
	private Item item;
	public void saveProductToDB(int id, String date, String name, int price, int qty, String status, String time,
			int totalPrice, String type, String user) {
	item.setId(id);
	item.setName(name);
	item.setPrice(price);
	item.setType(type);
	item.setDate(date);
	item.setQty(qty);
	item.setStatus(status);
	item.setUser(user);
	item.setTotalPrice(totalPrice);
	item.setTime(time);
		
		

         repository.save(item);
  
		


		// TODO Auto-generated method stub
		
	}
// TODO Auto-generated method stub
		
	
	

}
