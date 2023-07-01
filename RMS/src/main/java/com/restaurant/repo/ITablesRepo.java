package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.Tables;

public interface ITablesRepo extends JpaRepository<Tables, Integer> {

}
