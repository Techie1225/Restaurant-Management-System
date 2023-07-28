package com.restaurant.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.Tables;

@Repository
public interface ITableRepo extends CrudRepository<Tables, String> {

	Optional<Tables> findByTableNumber(String table_number);

}
