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
@Document(collection  ="Tables")
public class Tables {
	
	@Id
	private ObjectId _id;
	private String tableNumber;
	private Integer capacity;
	private String status;

}


