package com.restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Menu;
import com.restaurant.repo.IMenuRepo;

@Service
public class ItemService {

	@Autowired
	private IMenuRepo iMenuRepo;

	public Optional<Menu> findByitem(String item) {
		 return iMenuRepo.findByItem(item);
	}
	
	public Iterable<Menu> findAllitems() {
		 return iMenuRepo.findAll();
	}

	public Menu saveItem(Menu menu) {
		return iMenuRepo.save(menu);
	}

}
