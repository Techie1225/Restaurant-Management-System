package com.restaurant.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.restaurant.model.Waiter;

public interface IWaiterRepo extends CrudRepository<Waiter,Integer> {
	
	Optional<Waiter> findByEmailAndPassword(String email, String password);
	Optional<Waiter> findFirstByEmailOrPhone(String email, Long phone);

}
