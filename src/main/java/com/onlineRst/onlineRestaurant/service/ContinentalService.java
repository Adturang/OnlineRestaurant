package com.onlineRst.onlineRestaurant.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import com.onlineRst.onlineRestaurant.dao.ContinentalRepository;

import com.onlineRst.onlineRestaurant.model.Continental;

@Service
public class ContinentalService {
	@Autowired
	private ContinentalRepository repository;
	public void saveProductToDB(MultipartFile file,String name,int price,String type ) {
		Continental chinese=new Continental();
		String fileName=StringUtils.cleanPath(file.getOriginalFilename());
	   	System.out.println(fileName);
		if(fileName.contains("..")) {
			System.out.println("invalid File");
		}
		
		try {
			chinese.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		chinese.setName(name);
        chinese.setPrice(price);
        chinese.setType(type);

         repository.save(chinese);
  
		
         
	}
	

}
