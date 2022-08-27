package com.ibgregorio.bookinghotel.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibgregorio.bookinghotel.dto.ReservationDTO;
import com.ibgregorio.bookinghotel.entity.Reservation;
import com.ibgregorio.bookinghotel.services.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationResource {

	@Autowired
	private ReservationService reservationService;
	
	@Operation(summary = "List booked reservations by customer")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Show reservations booked for selected customer (empty if it does not contain any reservations)"),
			  @ApiResponse(responseCode = "404", description = "Customer not found", 
			  	content = @Content(mediaType = "application/json")) })
	@GetMapping
	public ResponseEntity<List<Reservation>> findReservationsByCustomer(
			@RequestParam(value = "customer", defaultValue = "") String idCustomerParam) {
		
		Long idCustomer = Long.parseLong(idCustomerParam);
		
		List<Reservation> list = reservationService.findReservationsByCustomer(idCustomer);
		
		return ResponseEntity.ok().body(list);
	}

	@Operation(summary = "Places a new reservation")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "201", description = "Reservation booked successfully"),
			  @ApiResponse(responseCode = "400", description = "Reservation with invalid period (greater than 3 days or 30 days in advance) or active reservation was found in selected room within given period", 
			  	content = @Content(mediaType = "application/json")), 
			  @ApiResponse(responseCode = "404", description = "Selected customer or Room not found", 
			  	content = @Content(mediaType = "application/json")),
			  @ApiResponse(responseCode = "422", description = "Validation error on reservation fields", 
			    content = @Content(mediaType = "application/json")) })
	@PostMapping
	public ResponseEntity<Void> placeReservation(@Valid @RequestBody ReservationDTO reservationDto) {
		Reservation reservationEntity = reservationService.buildEntityFromDTO(reservationDto);
		
		reservationEntity = reservationService.placeReservation(reservationEntity);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(reservationEntity.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@Operation(summary = "Modify an existing reservation")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "204", description = "Reservation updated successfully"),
			  @ApiResponse(responseCode = "400", description = "Reservation with invalid period (greater than 3 days or 30 days in advance) or active reservation was found in selected room within given period", 
			  	content = @Content(mediaType = "application/json")), 
			  @ApiResponse(responseCode = "404", description = "Reservation not found", 
			  	content = @Content(mediaType = "application/json")),
			  @ApiResponse(responseCode = "422", description = "Validation error on reservation fields", 
			    content = @Content(mediaType = "application/json")) })
	@PutMapping(value = "/{idReservation}")
	public ResponseEntity<Void> modifyReservation(@Valid @RequestBody ReservationDTO reservationDto, @PathVariable Long idReservation) {
		Reservation reservationEntity = reservationService.buildEntityFromDTO(reservationDto);
		reservationEntity.setId(idReservation);
		reservationEntity = reservationService.modifyReservation(reservationEntity);
		
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Cancel an existing reservation")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "204", description = "Reservation cancelled successfully"),
			  @ApiResponse(responseCode = "404", description = "Reservation not found", 
			  	content = @Content(mediaType = "application/json")) })
	@DeleteMapping(value = "/{idReservation}")
	public ResponseEntity<Void> cancelReservation(@PathVariable Long idReservation) {
		reservationService.cancelReservation(idReservation);
		
		return ResponseEntity.noContent().build();
	}

}
