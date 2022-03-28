package com.onlineRst.onlineRestaurant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
	String name;
	int price;
	int qty;
	String type;
	
	int calculateTotalPrice() {
		return this.getPrice()*this.getQty();
	}
	int totalPrice;
}
