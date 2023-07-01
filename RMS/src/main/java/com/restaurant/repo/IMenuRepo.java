package com.restaurant.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Menu;

@Repository
public interface IMenuRepo extends CrudRepository<Menu, Integer> {

}
