package com.restaurant.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Billing {
	
	@Id
	private ObjectId _id;
	private ObjectId order_id;
	private Integer amount;
	private Integer split;

}
