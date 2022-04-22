package com.onlineRst.onlineRestaurant.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



import com.onlineRst.onlineRestaurant.dao.ProceedOrderRepository;
import com.onlineRst.onlineRestaurant.model.Continental;
import com.onlineRst.onlineRestaurant.model.ProceedOrder;

@Service
public class ProceedOrderService {
	@Autowired
	private ProceedOrderRepository repository;
	public void saveProductToDB(int id, String date, String name, int price, int qty, int totalPrice, String type,
			String user,String status) {
		ProceedOrder proceedOrder=new ProceedOrder();
		//String fileName=StringUtils.cleanPath(file.getOriginalFilename());
//	   	System.out.println(fileName);
//		if(fileName.contains("..")) {
//			System.out.println("invalid File");
//		}
//		
//		try {
//			proceedOrder.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
//		} catch (IOException e) {
//		
//			e.printStackTrace();
//		}
		proceedOrder.setId(id);
		proceedOrder.setName(name);
		proceedOrder.setPrice(price);
		proceedOrder.setType(type);
		proceedOrder.setDate(date);
		proceedOrder.setQty(qty);
		proceedOrder.setStatus(status);
		proceedOrder.setUser(user);
		proceedOrder.setTotalPrice(totalPrice);
		
		

         repository.save(proceedOrder);
  
		
         
	}

		// TODO Auto-generated method stub
		
	
	

}
