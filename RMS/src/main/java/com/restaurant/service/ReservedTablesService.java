package com.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.ReservedTables;
import com.restaurant.repo.IReservedTablesRepo;

@Service
public class ReservedTablesService {
	
	@Autowired
	private IReservedTablesRepo iReservedTablesRepo;

	public ReservedTables reserveTable(ReservedTables reserveTable) {
		return iReservedTablesRepo.save(reserveTable);
		
	}

}
