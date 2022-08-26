package com.ibgregorio.bookinghotel.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibgregorio.bookinghotel.entity.Customer;
import com.ibgregorio.bookinghotel.repository.CustomerRepository;
import com.ibgregorio.bookinghotel.services.exception.ObjectNotFoundException;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer findCustomerById(Long idCustomer) {
		Optional<Customer> customer = customerRepository.findById(idCustomer);

		return customer.orElseThrow(() -> new ObjectNotFoundException("Customer not found! Id: " + idCustomer));
	}
}
