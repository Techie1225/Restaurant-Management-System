package com.restaurant.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.ReservedTables;

@Repository
public interface IReservedTablesRepo extends CrudRepository<ReservedTables,Integer> {

}
