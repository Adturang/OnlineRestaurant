package com.onlineRst.onlineRestaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineRst.onlineRestaurant.model.NonVegetarian;
import com.onlineRst.onlineRestaurant.model.ProceedOrder;

@Repository

 public interface ProceedOrderRepository extends CrudRepository<ProceedOrder, Integer>{

}
