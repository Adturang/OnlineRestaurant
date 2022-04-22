package com.onlineRst.onlineRestaurant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.onlineRst.onlineRestaurant.model.Item;

public interface ItemsRepository extends CrudRepository<Item, Integer> {
	
	List<Item> findByDateAndUser(String date,String user);
	
	List<Item> getItemByType(String typeOfItem);

	@Query("SELECT u FROM Items u")
	List<Item> findAllItem();

	@Query("select a from Items a where a.date = :date")
	List<Item> findAllWithCreationDateTimeBefore(@Param("date") String date);

	//@Query("select a from Items a where a.date1 >= :creationDateTime and a.date2 < :date2")
	@Query(value = "SELECT * from Items e where e.date between :initialDate and :finalDate", nativeQuery = true)
	public List<Item> findAllWithInRange(@Param("initialDate") String initialDate,
			@Param("finalDate") String finalDate);

	public List<Item> findTop3ByOrderByQtyDesc();

	@Query(value = "select * ,count(*) AS cnt from items\r\n" + 
			"group by name\r\n" + 
			"order by cnt desc limit 3", nativeQuery = true)
	public List<Item> getTop3Item();

	@Query("select u from Items u where u.name= :name")
	public List<Item> getItemByName(@Param("name") String name);

}
