package com.ibgregorio.bookinghotel.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibgregorio.bookinghotel.entity.Customer;
import com.ibgregorio.bookinghotel.entity.Room;
import com.ibgregorio.bookinghotel.repository.CustomerRepository;
import com.ibgregorio.bookinghotel.repository.RoomRepository;

@Configuration
public class TestConfig {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Bean
	public void createMockEntities() throws Exception {
		// Initializes test environment with pre-built customers
		Customer testCustomer1 = new Customer(null, "John Doe", "johndoe@email.com", "+55 71 3333-2222");
		Customer testCustomer2 = new Customer(null, "Mary Griffin", "mgriffin@email.com", "+55 11 9999-8888");
		
		customerRepository.saveAll(Arrays.asList(testCustomer1, testCustomer2));
		
		// Initializes test environment with pre-built rooms
		Room room1 = new Room(102L, 100.00, "Essential", 1, false);
		Room room2 = new Room(507L, 579.00, "Comfort", 2, false);
		Room room3 = new Room(703L, 972.00, "Presidential", 3, true);
		
		roomRepository.saveAll(Arrays.asList(room1, room2, room3));
	}

}
