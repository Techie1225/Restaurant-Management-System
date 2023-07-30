package com.restaurant.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class orderItems {
	private ObjectId id;
	private String itemid;
	private Integer quantity;
}
