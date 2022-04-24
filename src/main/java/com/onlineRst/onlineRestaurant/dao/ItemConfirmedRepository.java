package com.onlineRst.onlineRestaurant.dao;



import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlineRst.onlineRestaurant.model.History;
import com.onlineRst.onlineRestaurant.model.ItemConfirmed;
@Repository
public interface ItemConfirmedRepository extends CrudRepository<ItemConfirmed,Integer> {
	@Query("select u from ItemConfirmed u where u.name= :name")
	public ItemConfirmed getItemByName(@Param("name") String name);
	public ItemConfirmed findById(int id);

//	@Query("select u from History u where u.user=:k")
//	public List<History> findAllByuserName(@Param("k")String user);

	
    @Transactional
    @Modifying      // to mark delete or update query
    @Query(value = "DELETE FROM ItemConfirmed e WHERE e.name = :name")       // it will delete all the record with specific name
    int deleteByName(@Param("name") String name);
    
    @Transactional
    @Modifying      // to mark delete or update query
    @Query(value = "DELETE FROM ItemConfirmed e WHERE e.user=:user")       // it will delete all the record with specific name
    int deleteByUser(@Param("user") String user);
    
	@Query("select u from ItemConfirmed u where u.user=:k")
	public List<ItemConfirmed> findAllByuserName(@Param("k")String user);
	
	
	
	 
    @Transactional
    @Modifying      // to mark delete or update query
    @Query(value = "DELETE FROM ItemConfirmed e WHERE e.name = :name and e.user=:user")       // it will delete all the record with specific name
    int deleteByNameAndUser(@Param("name") String name,@Param("user") String user);
}