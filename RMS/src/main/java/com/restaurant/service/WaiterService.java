package com.restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Waiter;
import com.restaurant.repo.IWaiterRepo;

@Service
public class WaiterService {
	
	@Autowired
	private IWaiterRepo iWaiterRepo;

	public Waiter saveCustomer(Waiter waiter) {
		
		return iWaiterRepo.save(waiter);
	}

	public Optional<Waiter> findByEmailAndPassword(String email, String password) {
		return iWaiterRepo.findByEmailAndPassword(email, password);
	}

	public Optional<Waiter> findByEmailOrPhone(String email, Long phone) {
		return iWaiterRepo.findFirstByEmailOrPhone(email, phone);
	}

}
