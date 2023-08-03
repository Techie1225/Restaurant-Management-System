package com.restaurant.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
//	private String to_date_convert;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime to_date;

}
