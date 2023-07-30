package com.restaurant.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.model.Customer;
import com.restaurant.model.ReservedTables;
import com.restaurant.model.Tables;
import com.restaurant.repo.ICustomerRepo;
import com.restaurant.repo.IReservedTablesRepo;
import com.restaurant.repo.ITableRepo;

@Service
public class CustomerService {
	
	@Autowired
	private ICustomerRepo iCustomerRepo;
	
	@Autowired
	private ITableRepo iTableRepo;
	
	@Autowired
	private IReservedTablesRepo iReservedTablesRepo;
	
	private ReservedTables reservedTables;

	public ObjectId saveCustomer(Customer customer) {
		 reservedTables = new ReservedTables();
		 reservedTables.setTo_date(customer.getTo_date());
		 customer.setTo_date(null);
		 ObjectId table_id = getFreeTable(customer.getNo_of_people());
		 if(table_id!=null) {
			 reservedTables.setReserved_table_id(table_id);
			 ObjectId customer_id = iCustomerRepo.save(customer).get_id();
			 reservedTables.setCustomer_id(customer_id);
			 ObjectId reserve_id = iReservedTablesRepo.save(reservedTables).get_id();
			 return reserve_id;
		 }
		 else {
			 return null;
		 }
	}
	
	public ObjectId getFreeTable(int capacity) {
		Iterable<Tables> tabs=iTableRepo.findAll();
		List<Tables> arraylist = new ArrayList<Tables>();
		for(Tables tab : tabs) {
			arraylist.add(tab);
		}
		Collections.sort(arraylist,new Comparator<Tables>(){
		    public int compare(Tables tables1, Tables tables2) {
		        return tables1.getCapacity() - tables2.getCapacity();
		    }
		});
		for(Tables tab : arraylist) {
			System.out.println(tab);
			if(tab.getStatus().equalsIgnoreCase("Not Occupied")) {
				if(tab.getCapacity()>=capacity) {
					tab.setStatus("Occupied");
					iTableRepo.save(tab);
					return tab.get_id();
				}
			}
		}
		return null;
	}

}
