package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.Menu;

public interface IMenuRepo extends JpaRepository<Menu, Integer> {

}
