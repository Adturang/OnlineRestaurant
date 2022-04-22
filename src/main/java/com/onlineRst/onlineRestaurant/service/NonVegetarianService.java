package com.onlineRst.onlineRestaurant.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.onlineRst.onlineRestaurant.dao.NonVegetarianRepository;
import com.onlineRst.onlineRestaurant.model.NonVegetarian;

@Service
public class NonVegetarianService {
	@Autowired
	private NonVegetarianRepository repository;
	public void saveProductToDB(MultipartFile file,String name,int price,int id,String type) {
		NonVegetarian nonvegeterian=new NonVegetarian();
		String fileName=StringUtils.cleanPath(file.getOriginalFilename());
	   	System.out.println(fileName);
		if(fileName.contains("..")) {
			System.out.println("invalid File");
		}
		
		try {
			nonvegeterian.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		nonvegeterian.setId(id);
        nonvegeterian.setName(name);
        nonvegeterian.setPrice(price);
         nonvegeterian.setType(type);
       
         repository.save(nonvegeterian);
  
		
         
	}
	

}
