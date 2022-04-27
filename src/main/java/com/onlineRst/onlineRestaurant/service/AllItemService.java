package com.onlineRst.onlineRestaurant.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.onlineRst.onlineRestaurant.dao.AllItemRepository;
import com.onlineRst.onlineRestaurant.model.AlltemAdded;

@Service
public class AllItemService {
	@Autowired
	AllItemRepository repository;
	public void saveProductToDB(MultipartFile file,String name,int price,String type ) {
		AlltemAdded added=new AlltemAdded();
		String fileName=StringUtils.cleanPath(file.getOriginalFilename());
	   	System.out.println(fileName);
		if(fileName.contains("..")) {
			System.out.println("invalid File");
		}
		
		try {
			added.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		added.setName(name);
		added.setPrice(price);
		added.setType(type);
	
         repository.save(added);
  
		
         
	}
//	  public List<Employee> findTop3ByOrderByIdAsc();
	

}
