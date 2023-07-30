package com.restaurant.repo;

import org.springframework.data.repository.CrudRepository;

import com.restaurant.model.Billing;

public interface IBillRepo extends CrudRepository<Billing, Integer> {

}
