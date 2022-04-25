package com.onlineRst.onlineRestaurant.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor

@ToString
@Component
@Entity

public class ProceedOrder {


	
	String user;
	@Id
	@GeneratedValue
	int id;
	String name;
	int price;
	int qty;
 String type;
	String date;
	String status;

	
	public int calculateTotalPrice() {
		return this.getPrice()*this.getQty();
	}
	int totalPrice;
}
