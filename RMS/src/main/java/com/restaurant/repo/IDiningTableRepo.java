package com.restaurant.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.DiningTables;

@Repository
public interface IDiningTableRepo extends CrudRepository<DiningTables, Integer> {

}
