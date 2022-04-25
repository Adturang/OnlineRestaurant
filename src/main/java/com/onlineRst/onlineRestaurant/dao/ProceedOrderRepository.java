package com.onlineRst.onlineRestaurant.dao;


import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


import com.onlineRst.onlineRestaurant.model.ProceedOrder;

@Repository

 public interface ProceedOrderRepository extends CrudRepository<ProceedOrder, Integer>{

}
