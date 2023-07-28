package com.restaurant.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Customers")
public class Customer {
	
	@Id
	private ObjectId _id;
	private String name;
	private Long phone;
	private Integer no_of_people;

}
