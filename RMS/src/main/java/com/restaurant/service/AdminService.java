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

	public Optional<Admin> findByUsernameAndPassword(String username, String password) {
		return iAdminRepo.findByUsernameAndPassword(username, password);
	}
	
	

}
