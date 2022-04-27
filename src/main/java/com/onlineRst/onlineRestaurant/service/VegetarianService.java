package com.onlineRst.onlineRestaurant.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.onlineRst.onlineRestaurant.dao.VegeterainRepository;
import com.onlineRst.onlineRestaurant.model.Vegeterian;

@Service
public class VegetarianService {
	@Autowired
	private VegeterainRepository repository;
	public void saveProductToDB(MultipartFile file,String name,int price,String type) {
		
		Vegeterian vegeterian=new Vegeterian();
		String fileName=StringUtils.cleanPath(file.getOriginalFilename());
	   	System.out.println(fileName);
		if(fileName.contains("..")) {
			System.out.println("invalid File");
		}
		
		try {
			vegeterian.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
        vegeterian.setName(name);
         vegeterian.setPrice(price);
  
         vegeterian.setType(type);
         repository.save(vegeterian);
  
		
         
	}
public void saveExistedProductToDB(String name,int price,int id,String type,   int itemNo ) {
		
		Vegeterian vegeterian=new Vegeterian();
		
		vegeterian.setId(id);
        vegeterian.setName(name);
         vegeterian.setPrice(price);
    
         vegeterian.setType(type);
         repository.save(vegeterian);
  
		
         
	}
	

	

}
