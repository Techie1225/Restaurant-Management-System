package com.restaurant.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.restaurant.model.Menu;

public interface IMenuRepo extends CrudRepository<Menu,String> {

	Optional<Menu> findByItem(String item);

}
