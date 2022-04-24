package com.onlineRst.onlineRestaurant.model;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.onlineRst.onlineRestaurant.dao.ItemsRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeDeleter extends TimerTask {
	
	@Autowired
	ItemsRepository irepo;
	int id;

	@Override
	public void run() {
		System.out.println("timer Called *****************");
		Item item =irepo.findById(id);
		if (item!=null) {
			irepo.delete(item);
		}
	}
	
	
}
