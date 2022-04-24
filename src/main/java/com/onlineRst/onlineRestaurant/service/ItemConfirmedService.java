package com.onlineRst.onlineRestaurant.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineRst.onlineRestaurant.dao.HistoryRepository;
import com.onlineRst.onlineRestaurant.dao.ItemConfirmedRepository;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.model.History;
import com.onlineRst.onlineRestaurant.model.Item;
import com.onlineRst.onlineRestaurant.model.ItemConfirmed;

@Service
public class ItemConfirmedService {
	
	@Autowired
	private ItemConfirmedRepository repository;
	@Autowired
	private ItemConfirmed confirmed;
	public void saveProductToDB(int id, String date, String name, int price,  String type, int qty, int tp, String status, String time,String user) {
		
		confirmed.setId(id);
		confirmed.setName(name);
		confirmed.setPrice(price);
		confirmed.setType(type);
		confirmed.setDate(date);
		confirmed.setTotalPrice(tp);
		confirmed.setQty(qty);
		confirmed.setStatus(status);
		confirmed.setTime(time);
		confirmed.setUser(user);
         repository.save(confirmed);
  
		


		// TODO Auto-generated method stub
		
	}
// TODO Auto-generated method stub

		
	
	

}