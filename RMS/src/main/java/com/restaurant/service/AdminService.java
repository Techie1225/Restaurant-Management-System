package com.restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.restaurant.model.Admin;
import com.restaurant.repo.IAdminRepo;

@Service
public class AdminService {
	
	@Autowired
	private IAdminRepo iAdminRepo;

	public Optional<Admin> findByEmailAndPassword(String email, String password) {
		return iAdminRepo.findByEmailAndPassword(email, password);
	}
	
	

}
