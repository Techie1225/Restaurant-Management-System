package com.restaurant.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservedTab {
	
	private ObjectId reservedid;
	private ObjectId orderid;
	private String name;
	private String table;
	private ObjectId custid;

}
