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
public class RateNReview {
	int rating;
	String review;
	@Id
	@GeneratedValue
	int id;
	String user;
	String oDate;
	String curDate;
	String name;
}
