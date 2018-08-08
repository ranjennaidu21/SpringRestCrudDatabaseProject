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
	
		
	
}


















