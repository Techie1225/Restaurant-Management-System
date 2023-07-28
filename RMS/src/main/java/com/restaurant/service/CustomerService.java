package com.restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Admin;
import com.restaurant.model.Customer;
import com.restaurant.repo.ICustomerRepo;

@Service
public class CustomerService {
	
	@Autowired
	private ICustomerRepo iCustomerRepo;

	public Customer saveCustomer(Customer customer) {
		
		return iCustomerRepo.save(customer);
	}

//	public Optional<Customer> findByEmailAndPassword(String email, String password) {
//		return iCustomerRepo.findByEmailAndPassword(email, password);
//	}
//
//	public Optional<Customer> findByEmailOrPhone(String email, Long phone) {
//		return iCustomerRepo.findFirstByEmailOrPhone(email, phone);
//	}

}
