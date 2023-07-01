package com.restaurant.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Registration;

@Repository
public interface IRegistrationRepo extends CrudRepository<Registration, Integer> {

}
