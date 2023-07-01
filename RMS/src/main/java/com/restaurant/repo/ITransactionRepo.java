package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.Transaction;

public interface ITransactionRepo extends JpaRepository<Transaction, Integer> {

}
