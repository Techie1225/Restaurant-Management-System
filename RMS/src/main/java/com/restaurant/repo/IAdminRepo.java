package com.restaurant.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Admin;

@Repository
public interface IAdminRepo extends CrudRepository<Admin, Integer>  {
	Optional<Admin> findByUsernameAndPassword(String username, String password);

}
