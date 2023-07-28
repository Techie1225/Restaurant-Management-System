package com.restaurant.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.restaurant.model.Waiter;

public interface IWaiterRepo extends CrudRepository<Waiter,Integer> {
	
	Optional<Waiter> findByUsernameAndPassword(String username, String password);
	Optional<Waiter> findFirstByUsername(String username);

}
