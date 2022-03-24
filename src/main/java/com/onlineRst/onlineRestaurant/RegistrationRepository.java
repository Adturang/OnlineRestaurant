package com.onlineRst.onlineRestaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<Registration, String>{

    @Query("SELECT u FROM Registration u WHERE u.userName = :userName")
    public Registration getRegistrationByUsername(String userName);
    //List<Registration> findAll();
}

