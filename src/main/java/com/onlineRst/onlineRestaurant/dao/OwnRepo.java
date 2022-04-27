package com.onlineRst.onlineRestaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineRst.onlineRestaurant.model.OwnRecipe;


@Repository
public interface OwnRepo extends CrudRepository<OwnRecipe, Integer>{
	
	@Query("select u from OwnRecipe u where u.uName=:k")
	public List<OwnRecipe> findAllByuserName(@Param("k")String uName);
}
