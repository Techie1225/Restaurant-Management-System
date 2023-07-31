package com.restaurant.repo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Tables;

@Repository
public interface ITableRepo extends CrudRepository<Tables, ObjectId> {

	Optional<Tables> findByTableNumber(String table_number);

	Tables findBy_id(ObjectId id);

}
