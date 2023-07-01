package com.restaurant.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Payment;

@Repository
public interface IPaymentRepo extends CrudRepository<Payment, Integer> {

}
