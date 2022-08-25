package com.ibgregorio.bookinghotel.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibgregorio.bookinghotel.dto.ReservationDTO;
import com.ibgregorio.bookinghotel.entity.Reservation;
import com.ibgregorio.bookinghotel.repository.CustomerRepository;
import com.ibgregorio.bookinghotel.repository.ReservationRepository;
import com.ibgregorio.bookinghotel.repository.RoomRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	
	public Reservation findReservationById(Long idReservation) {
		Optional<Reservation> reservation = reservationRepository.findById(idReservation);
		return reservation.orElse(null);
	}
	
	@Transactional
	public Reservation placeReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}
	
	public void cancelReservation(Long idReservation) {
		Reservation selectedReservation = findReservationById(idReservation);
		
		if (selectedReservation != null) {
			reservationRepository.deleteById(selectedReservation.getId());
		}
	}	
	
	public Reservation buildEntityFromDTO(ReservationDTO reservationDto) {
		Reservation reservation = new Reservation(
				reservationDto.getId(), 
				null, 
				null, 
				reservationDto.getStartDate(), 
				reservationDto.getEndDate());
		
		reservation.setCustomer(customerRepository.findById(reservationDto.getIdCustomer()).orElse(null));
		reservation.setRoom(roomRepository.findById(reservationDto.getIdRoom()).orElse(null));
				
		return reservation;
	}

	
	
}
