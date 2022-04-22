package com.onlineRst.onlineRestaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.onlineRst.onlineRestaurant.model.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, String>{

    @Query("SELECT u FROM Registration u WHERE u.userName = :userName")
    public Registration getRegistrationByUsername(String userName);
    //List<Registration> findAll();

    @Query("SELECT u FROM Registration u")
	public List<Registration> findAllActiveUsers();
    
}

