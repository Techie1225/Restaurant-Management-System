package com.restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Tables;
import com.restaurant.repo.ITableRepo;

@Service
public class TableService {
	
	@Autowired
	private ITableRepo iTableRepo;
	
	public Optional<Tables> findByTableNumber(String table_number) {
		 return iTableRepo.findByTableNumber(table_number);
	}
	
	public Iterable<Tables> findAllTables() {
		 return iTableRepo.findAll();
	}

	public Tables saveItem(Tables table) {
		return iTableRepo.save(table);
	}

}
