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
@Entity
public class OwnRecipe {
	@Id
	@GeneratedValue
	int id;
	String uName;
	String name;
	String recDesc;
	String type;
	String date;
	int price;
	int qty;
	int time;
	public String status;
	public int calculateTotalPrice() {
		return this.getPrice()*this.getQty();
	}
	int totalPrice;
	
}
