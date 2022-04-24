package com.onlineRst.onlineRestaurant.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineRst.onlineRestaurant.dao.HistoryRepository;
import com.onlineRst.onlineRestaurant.dao.ItemsRepository;
import com.onlineRst.onlineRestaurant.model.History;
import com.onlineRst.onlineRestaurant.model.Item;

@Service
public class HistoryService {
	
	@Autowired
	private HistoryRepository repository;
	@Autowired
	private History history;
	public void saveProductToDB(int id, String date, String name, int price,  String type, int qty, int tp, String status, String time,String user) {
		history.setId(id);
	history.setName(name);
	history.setPrice(price);
	history.setType(type);
	history.setDate(date);
	history.setTotalPrice(tp);
	history.setQty(qty);
	history.setStatus(status);
	history.setTime(time);
	history.setUser(user);
         repository.save(history);
  
		


		// TODO Auto-generated method stub
		
	}
// TODO Auto-generated method stub

		
	
	

}