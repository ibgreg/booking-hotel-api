package com.ibgregorio.bookinghotel.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibgregorio.bookinghotel.dto.CustomerDTO;
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
	
	public List<Customer> listAllCustomers() {
		return customerRepository.findAll();
	}
	
	@Transactional
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Transactional
	public Customer updateCustomer(Customer customer) {
		Customer modifiedCustomer = findCustomerById(customer.getId());
		updateReservationData(modifiedCustomer, customer);
		
		
		return customerRepository.save(modifiedCustomer);
	}
	
	public Customer buildEntityFromDTO(CustomerDTO customerDto) {		
		return new Customer(
				customerDto.getId(), 
				customerDto.getName(), 
				customerDto.getEmail(), 
				customerDto.getPhoneNumber());
	}
	
	private void updateReservationData(Customer modifiedCustomer, Customer customer) {
		modifiedCustomer.setName(customer.getName());
		modifiedCustomer.setEmail(customer.getEmail());
		modifiedCustomer.setPhoneNumber(customer.getPhoneNumber());
	}
}
