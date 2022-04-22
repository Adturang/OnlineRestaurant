package com.onlineRst.onlineRestaurant.dao;

import org.springframework.data.repository.CrudRepository;

import com.onlineRst.onlineRestaurant.model.OwnRecipe;



public interface OwnRepo extends CrudRepository<OwnRecipe, Integer>{

}
