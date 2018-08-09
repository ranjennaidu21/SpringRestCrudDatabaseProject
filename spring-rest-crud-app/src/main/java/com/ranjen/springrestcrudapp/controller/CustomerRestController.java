package com.ranjen.springrestcrudapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjen.springrestcrudapp.entity.Customer;
import com.ranjen.springrestcrudapp.error.exception.CustomerNotFoundException;
import com.ranjen.springrestcrudapp.service.CustomerService;

//Take note we have added Spring Security to secure the REST Endpoint.
//So we need username and password to access the REST Endpoint.
/*
 Postman will prompt for new credentials for
each REST request.
1. Run your REST application.
2. In Postman access the REST endpoint: GET /api/customers
You will initial get a 401 error: Unauthorized
3. To resolve this, in Postman, click the "Authorization" section of the request.
4. In the "Type" drop-down list, select "Basic Auth"
//Based on the username , there have different role which have access to certain function
//refer to DemoSecurityConfig.java for that
5. Enter user id: john, password: test123
6. Using Postman send the request again
If the user is authenticated, then they'll get the results of REST request. If not, they'll
see a 401 error
*/

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
	
	// add mapping for PUT /customers - update existing customer
	//TO TEST: Use POSTMAN , choose PUT , paste the/customers url , click body tab and raw box,
	//then select from dropdown JSON(application/json)
	
	//LET UPDATE ID NO 9 
	//the add as following before send
/*	
	{
	    "id": 9,
	    "firstName": "Kaala",
	    "lastName": "Karikalan",
	    "email": "k_kala@test.com"
	}
	*/
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		
		//since customer id is set, DAO will update customer in the database
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
		
	}
	
	// add mapping for DELETE /customers/{customerId} - delete customer
	//TO TEST: Use POSTMAN , choose DELETE , paste the/customers/8 url to delete customer
	//id 8, click body tab and raw box,
	//then select from dropdown JSON(application/json)
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		
		Customer tempCustomer = customerService.getCustomer(customerId);
		
		// throw exception if Customer is null
		
		if (tempCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
				
		customerService.deleteCustomer(customerId);
		
		return "Deleted customer id - " + customerId;
	}
	

	
		
	
}


















