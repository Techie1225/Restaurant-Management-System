package com.restaurant.repo;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Customer;
import com.restaurant.model.ReservedTables;

@Repository
public interface IReservedTablesRepo extends CrudRepository<ReservedTables,Integer> {
	ReservedTables findBy_id(ObjectId id);
}
