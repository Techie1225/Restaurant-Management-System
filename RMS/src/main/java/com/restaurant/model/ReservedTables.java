package com.restaurant.model;


import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ReservedTables")
public class ReservedTables {
	
	@Id
	private ObjectId _id;
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime to_date;
	private ObjectId customer_id;
	private ObjectId waiter_id;
	private ObjectId reserved_table_id;

}
