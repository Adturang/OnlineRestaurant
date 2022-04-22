package com.onlineRst.onlineRestaurant.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@Entity(name = "Items")
public class Item {
	

	String user;
	@Id
	@GeneratedValue
	int id;
	public String name;
	int price;
	int qty;
	public String type;
	String date;
	int time;
	public String status;

	
	public int calculateTotalPrice() {
		return this.getPrice()*this.getQty();
	}
	int totalPrice;
}
