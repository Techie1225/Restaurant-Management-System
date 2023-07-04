package com.restaurant.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Admin;
import com.restaurant.model.Customer;

@Repository
public interface ICustomerRepo extends CrudRepository<Customer, Integer> {

	Optional<Customer> findByEmailAndPassword(String email, String password);
	Optional<Customer> findFirstByEmailOrPhone(String email, Long phone);
}
