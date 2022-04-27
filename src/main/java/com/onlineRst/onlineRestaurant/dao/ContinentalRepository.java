package com.onlineRst.onlineRestaurant.dao;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.onlineRst.onlineRestaurant.model.Continental;


@Repository
public interface ContinentalRepository extends CrudRepository<Continental, Integer>{
	 @Modifying      // to mark delete or update query
	    @Query(value = "DELETE FROM Continental e WHERE e.id = :id")       // it will delete all the record with specific name
	    int deleteById(@Param("id") int id);
	 @Query("select u from Continental u where u.id= :x")
	 public Continental getItemById(@Param("x")int id);
	    @Transactional
	    @Modifying      // to mark delete or update query
	    @Query(value = "DELETE FROM Continental e WHERE e.name = :name")       // it will delete all the record with specific name
	    int deleteByName(@Param("name") String name);
	    
	    @Query(value="select * from Continental where name= :name",nativeQuery=true)
	  	 public Continental getItemByName(@Param("name")String name);
	    @Transactional
	    @Modifying
	    @Query(value="update Continental u set u.price = :price where u.id = :id")
	    void updateMethod(@Param("price") int price,@Param("id") int id);
	 
}
