package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.Payment;

public interface IPaymentRepo extends JpaRepository<Payment, Integer> {

}
