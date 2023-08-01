package com.restaurant.repo;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Customer;

@Repository
public interface ICustomerRepo extends CrudRepository<Customer, ObjectId> {

//	Optional<Customer> findByEmailAndPassword(String email, String password);
//	Optional<Customer> findFirstByEmailOrPhone(String email, Long phone);
	Customer findBy_id(ObjectId id);

	void deleteBy_id(ObjectId customerid);
}
