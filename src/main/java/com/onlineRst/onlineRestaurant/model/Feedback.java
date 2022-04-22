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
public class Feedback {
	@Id
	@GeneratedValue
	int id;
	String user;
	String date;
	String msg;
}
