package com.onlineRst.onlineRestaurant.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onlineRst.onlineRestaurant.model.AlltemAdded;



import org.springframework.transaction.annotation.Transactional;
public interface AllItemRepository extends CrudRepository<AlltemAdded ,Integer> {
	 @Modifying      // to mark delete or update query
	    @Query(value = "DELETE FROM AlltemAdded e WHERE e.id = :id")       // it will delete all the record with specific name
	    int deleteById(@Param("id") int id);
	 @Transactional
	   @Modifying      // to mark delete or update query
	    @Query(value = "DELETE FROM AlltemAdded e WHERE e.name = :name")       // it will delete all the record with specific name
	    int deleteByName(@Param("name") String name);
}
	 
