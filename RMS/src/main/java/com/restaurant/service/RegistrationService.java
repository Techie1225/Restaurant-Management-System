package com.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Customer;
import com.restaurant.repo.IRegistrationRepo;

@Service
public class RegistrationService {
	
	@Autowired
	private IRegistrationRepo iRegistrationRepo;

	public boolean saveCustomer(Customer customer) {
		
		return iRegistrationRepo.save(customer) != null;
		
	}

}
