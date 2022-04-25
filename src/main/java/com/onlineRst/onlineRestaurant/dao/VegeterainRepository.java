package com.onlineRst.onlineRestaurant.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.onlineRst.onlineRestaurant.model.Vegeterian;


@Repository
public interface VegeterainRepository extends CrudRepository<Vegeterian, Integer>{
    @Modifying      // to mark delete or update query
    @Query(value = "DELETE FROM Vegeterian e WHERE e.id = :id")       // it will delete all the record with specific name
    int deleteById(@Param("id") int id);
    @Query("select u from Vegeterian u where u.id= :x")
	 public Vegeterian getItemById(@Param("x")int id);
    @Transactional
    @Modifying      // to mark delete or update query
    @Query(value = "DELETE FROM Vegeterian e WHERE e.name = :name")       // it will delete all the record with specific name
    int deleteByName(@Param("name") String name);
	
}
