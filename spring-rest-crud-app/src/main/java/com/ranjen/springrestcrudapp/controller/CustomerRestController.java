package com.ranjen.springrestcrudapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjen.springrestcrudapp.entity.Customer;
import com.ranjen.springrestcrudapp.error.exception.CustomerNotFoundException;
import com.ranjen.springrestcrudapp.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// autowire the CustomerService
	@Autowired
	private CustomerService customerService;
	
	// add mapping for GET /customers
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		
		return customerService.getCustomers();
		
	}
	
	// add mapping for GET /customers/{customerId}
	
	@GetMapping("/customers/{customerId}")
	//the pathvariable need to be same as variable name inside the bracket above
	public Customer getCustomer(@PathVariable int customerId) {
		
		Customer theCustomer = customerService.getCustomer(customerId);
		//if customer id not found in database , return null
		//For null objects , jackson return empty body
		//we need to handle exeption for that
		if (theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
		
		return theCustomer;
	}
	
	// add mapping for POST /customers  - add new customer
	//TO TEST: Use POSTMAN , choose POST , paste the/customers url , click body tab and raw box,
	//then select from dropdown JSON(application/json)
	
	//the add as following before send
/*	
 	{
		"firstName" : "Ranjen",
		"lastName" : "Naidu",
		"email" : "ranjennaidu@test.com"
	}
	*/
	@PostMapping("/customers")
	//now we can access the requestbody as POJO
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		// also just in case the pass an id in JSON ... set id to 0
		//we use hibernate , using the method saveOrUpdate in DAO, so if it is set to 0
		//hibernate will take it as empty or null which 
		//this is force a save of new item ... instead of update
		
		theCustomer.setId(0);
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	

	
		
	
}


















