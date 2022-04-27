package com.onlineRst.onlineRestaurant.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.onlineRst.onlineRestaurant.model.History;
@Repository
public interface HistoryRepository extends CrudRepository<History,Integer> {
	@Query("select u from History u where u.date=:date and u.user=:user")
	public List<History> findByDateAndUser(@Param("date")String date,@Param("user")String user);
	
	@Query("select u from History u where u.user=:k")
	public List<History> findAllByuserName(@Param("k")String user);
	
	@Query(value="SELECT * from History a where a.date = :date",nativeQuery=true)
	List<History> findByDate(@Param("date") String date);

	//@Query("select a from Items a where a.date1 >= :creationDateTime and a.date2 < :date2")
	@Query(value = "SELECT * from History e where e.date between :initialDate and :finalDate", nativeQuery = true)
	public List<History> findAllWithInRange(@Param("initialDate") String initialDate,
			@Param("finalDate") String finalDate);
	
	List<History> getItemByType(String typeOfItem);
	
}
