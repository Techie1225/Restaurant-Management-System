package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.DiningTables;

public interface IDiningTableRepo extends JpaRepository<DiningTables, Integer> {

}
