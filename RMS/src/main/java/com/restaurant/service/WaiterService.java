package com.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.ReservedTables;
import com.restaurant.model.Waiter;
import com.restaurant.repo.IReservedTablesRepo;
import com.restaurant.repo.ITableRepo;
import com.restaurant.repo.IWaiterRepo;

@Service
public class WaiterService {
	
	@Autowired
	private IWaiterRepo iWaiterRepo;
	
	@Autowired
	private ITableRepo iTableRepo;
	
	@Autowired
	private IReservedTablesRepo iReservedTablesRepo;

	public Waiter saveCustomer(Waiter waiter) {
		
		return iWaiterRepo.save(waiter);
	}

	public Optional<Waiter> findByUsernameAndPassword(String username, String password) {
		return iWaiterRepo.findByUsernameAndPassword(username, password);
	}

	public Optional<Waiter> findByUsername(String username) {
		return iWaiterRepo.findFirstByUsername(username);
	}
	
	public List<ReservedTables> findAllTables() {
		List<ReservedTables> li = new ArrayList<ReservedTables>();
		for(ReservedTables tab: iReservedTablesRepo.findAll()) {
			if(tab.getWaiter_id()==null) {
				li.add(tab);
			}
		}
		return li;
	}
	
	

}
