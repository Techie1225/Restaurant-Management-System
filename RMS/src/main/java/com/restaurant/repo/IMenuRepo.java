package com.restaurant.repo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.restaurant.model.Menu;

public interface IMenuRepo extends CrudRepository<Menu,ObjectId> {

	Optional<Menu> findByItem(String item);

	Menu findBy_id(ObjectId id);

	Menu deleteBy_id(ObjectId menu_id);

}
