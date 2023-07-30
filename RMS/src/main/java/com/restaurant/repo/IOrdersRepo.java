package com.restaurant.repo;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Orders;

@Repository
public interface IOrdersRepo extends CrudRepository<Orders, Integer> {
	Orders findByreservedid(ObjectId id);
}
