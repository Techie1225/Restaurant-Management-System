package com.restaurant.repo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.restaurant.model.Waiter;

public interface IWaiterRepo extends CrudRepository<Waiter,ObjectId> {
	
	Optional<Waiter> findByUsernameAndPassword(String username, String password);
	Optional<Waiter> findFirstByUsername(String username);
	Optional<Waiter> findBy_id(ObjectId waiterid);

}
