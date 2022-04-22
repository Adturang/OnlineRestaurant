package com.onlineRst.onlineRestaurant.dao;

import org.springframework.data.repository.CrudRepository;

import com.onlineRst.onlineRestaurant.model.Feedback;

public interface FeedbackRepo extends CrudRepository<Feedback, Integer>{

}
