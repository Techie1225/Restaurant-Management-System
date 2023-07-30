package com.restaurant.model;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Orders")
public class Orders {
	
	@Id
	private ObjectId _id;
	private ObjectId reservedid;
	private ObjectId waiter_id;
	private Object itemsid;

}
