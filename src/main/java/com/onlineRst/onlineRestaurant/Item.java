package com.onlineRst.onlineRestaurant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
	
	@OneToOne(cascade = CascadeType.ALL,targetEntity = Registration.class)
	@JoinColumn(name = "user", referencedColumnName = "userName")
	String userName;
	@Id
	int id;
	String name;
	int price;
	int qty;
	String type;
	
	int calculateTotalPrice() {
		return this.getPrice()*this.getQty();
	}
	int totalPrice=calculateTotalPrice();
}
