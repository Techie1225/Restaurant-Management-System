package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.Orders;

public interface IOrdersRepo extends JpaRepository<Orders, Integer> {

}
