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

	public Optional<Waiter> findByUsernameAndPassword(String username, String password) {
		return iWaiterRepo.findByUsernameAndPassword(username, password);
	}

	public Optional<Waiter> findByUsername(String username) {
		return iWaiterRepo.findFirstByUsername(username);
	}

}
