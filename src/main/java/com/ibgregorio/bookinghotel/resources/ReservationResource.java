package com.ibgregorio.bookinghotel.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibgregorio.bookinghotel.dto.ReservationDTO;
import com.ibgregorio.bookinghotel.entity.Reservation;
import com.ibgregorio.bookinghotel.services.ReservationService;

@RestController
@RequestMapping(value = "/resources")
public class ReservationResource {

	@Autowired
	private ReservationService reservationService;
	
	@PostMapping
	public ResponseEntity<Void> placeReservation(@Valid @RequestBody ReservationDTO reservationDto) {
		Reservation reservationEntity = reservationService.buildEntityFromDTO(reservationDto);
		
		reservationEntity = reservationService.placeReservation(reservationEntity);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(reservationEntity.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/{idReservation}")
	public ResponseEntity<Void> cancelReservation(@PathVariable Long idReservation) {
		reservationService.cancelReservation(idReservation);
		
		return ResponseEntity.noContent().build();
	}

}
