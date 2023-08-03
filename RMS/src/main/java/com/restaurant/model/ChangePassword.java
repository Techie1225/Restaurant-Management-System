package com.restaurant.model;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {

	private ObjectId waiterid;
	private String currentpassword;
	private String newpassword;
	
}

