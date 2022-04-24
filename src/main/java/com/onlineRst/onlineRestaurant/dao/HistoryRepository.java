package com.onlineRst.onlineRestaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlineRst.onlineRestaurant.model.History;
import com.onlineRst.onlineRestaurant.model.Item;
@Repository
public interface HistoryRepository extends CrudRepository<History,Integer> {


	@Query("select u from History u where u.user=:k")
	public List<History> findAllByuserName(@Param("k")String user);


}
