package com.onlineRst.onlineRestaurant.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class Registration {
	
	
	String name;
	long number;
	String email;
	String address;
	@Id
	String userName;
	String password;
	String role="User";
}
