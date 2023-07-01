package com.restaurant.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Orders;

@Repository
public interface IOrdersRepo extends CrudRepository<Orders, Integer> {

}
