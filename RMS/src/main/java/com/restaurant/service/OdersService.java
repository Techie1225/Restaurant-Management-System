package com.restaurant.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Orders;
import com.restaurant.repo.IOrdersRepo;

@Service
public class OdersService {
	
	@Autowired
	private IOrdersRepo iOrdersRepo;

	public ObjectId saveOrders(Orders order) {
		// TODO Auto-generated method stub
		return iOrdersRepo.save(order).get_id();
	}

}
