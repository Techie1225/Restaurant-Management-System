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
@Document(collection = "Payment")
public class Payment {

	@Id
	private ObjectId _id;
	private Long card_number;
	private String expiry_date;
	private Integer cvv;
	private String name_on_the_card;
	private ObjectId customer_id;
	private ObjectId order_id;
}
