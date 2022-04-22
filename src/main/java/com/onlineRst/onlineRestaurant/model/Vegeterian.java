package com.onlineRst.onlineRestaurant.model;

import java.awt.image.BufferedImage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

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
public class Vegeterian {
	@Id
	@GeneratedValue

	int id;
	  
	String name;
	int price;
    String type;
    String date;


	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;

}
