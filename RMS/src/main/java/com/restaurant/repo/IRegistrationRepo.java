package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.Registration;

public interface IRegistrationRepo extends JpaRepository<Registration, Integer> {

}
