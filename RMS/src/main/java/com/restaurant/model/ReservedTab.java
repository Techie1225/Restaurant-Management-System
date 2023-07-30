package com.restaurant.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservedTab {
	
	private ObjectId reserved_id;
	private String name;
	private String table;

}
