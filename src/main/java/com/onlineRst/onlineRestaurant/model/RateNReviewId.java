package com.onlineRst.onlineRestaurant.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class RateNReviewId implements Serializable {
	String user;
	String curDate;
	String name;
	

}
