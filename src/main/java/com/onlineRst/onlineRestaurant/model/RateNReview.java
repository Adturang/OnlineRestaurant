package com.onlineRst.onlineRestaurant.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@Entity
@IdClass(RateNReviewId.class)
public class RateNReview {
	int rating;
	String review;
	@Id
	String user;
	String oDate;
	@Id
	String curDate;
	@Id
	String name;
}
