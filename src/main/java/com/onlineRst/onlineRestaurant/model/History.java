package com.onlineRst.onlineRestaurant.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import org.springframework.stereotype.Component;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class History {
	@Id
	@GeneratedValue

	int id;
	  
	String name;
	int price;
    String type;
    String date;
    int qty;
    String status;
   String user;
   String time;
	public int calculateTotalPrice() {
		return this.getPrice()*this.getQty();
	}
	int totalPrice;



	
	

}
