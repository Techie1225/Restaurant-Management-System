package com.restaurant.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class itemsquantity {

	private String item;
	private Integer quantity;
	private Float price;
	private String _id;
	private String reserveid;
}
