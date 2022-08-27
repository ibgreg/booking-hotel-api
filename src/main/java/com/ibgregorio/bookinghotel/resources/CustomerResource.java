package com.ibgregorio.bookinghotel.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibgregorio.bookinghotel.dto.CustomerDTO;
import com.ibgregorio.bookinghotel.entity.Customer;
import com.ibgregorio.bookinghotel.services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;
	
	@Operation(summary = "Find Customer by given ID")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Returns data from found Customer"),
			  @ApiResponse(responseCode = "404", description = "Customer not found", 
			  	content = @Content(mediaType = "application/json")) })
	@GetMapping(value = "/{idCustomer}")
	public ResponseEntity<Customer> findCustomerById(@PathVariable Long idCustomer) {
		Customer customer = customerService.findCustomerById(idCustomer);
		
		return ResponseEntity.ok().body(customer);
	}
	
	@Operation(summary = "List all registered customers")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Returns a list containing registered customers")})
	@GetMapping
	public ResponseEntity<List<Customer>> listAllCustomers() {
		List<Customer> customersList = customerService.listAllCustomers();
		
		return ResponseEntity.ok().body(customersList);
	}

	@Operation(summary = "Creates a new customer registry")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "201", description = "Reservation booked successfully"),
			  @ApiResponse(responseCode = "422", description = "Validation error on Customer fields", 
			    content = @Content(mediaType = "application/json")) })
	@PostMapping
	public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		Customer customerEntity = customerService.buildEntityFromDTO(customerDTO);
		
		customerEntity = customerService.createCustomer(customerEntity);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(customerEntity.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@Operation(summary = "Updates an existing customer informations")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "204", description = "Customer updated successfully"),
			  @ApiResponse(responseCode = "404", description = "Customer not found", 
			  	content = @Content(mediaType = "application/json")),
			  @ApiResponse(responseCode = "422", description = "Validation error on Customer fields", 
			    content = @Content(mediaType = "application/json")) })
	@PutMapping(value = "/{idCustomer}")
	public ResponseEntity<Void> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO, @PathVariable Long idCustomer) {
		Customer customerEntity = customerService.buildEntityFromDTO(customerDTO);
		
		customerEntity = customerService.updateCustomer(customerEntity);
		
		return ResponseEntity.noContent().build();
	}
}
