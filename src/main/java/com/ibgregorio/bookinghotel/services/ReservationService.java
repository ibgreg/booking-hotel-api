package com.ibgregorio.bookinghotel.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibgregorio.bookinghotel.dto.ReservationDTO;
import com.ibgregorio.bookinghotel.entity.Reservation;
import com.ibgregorio.bookinghotel.repository.ReservationRepository;
import com.ibgregorio.bookinghotel.services.exception.ObjectNotFoundException;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RoomService roomService;
	
	
	public Reservation findReservationById(Long idReservation) {
		Optional<Reservation> reservation = reservationRepository.findById(idReservation);
		
		return reservation.orElseThrow(() -> new ObjectNotFoundException("Reservation not found! Id: " + idReservation));
	}
	
	@Transactional
	public Reservation placeReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}
	
	@Transactional
	public Reservation modifyReservation(Reservation reservation) {
		Reservation modifiedReservation = findReservationById(reservation.getId());
		updateReservationData(modifiedReservation, reservation);
		
		return reservationRepository.save(modifiedReservation);
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
		
		reservation.setCustomer(customerService.findCustomerById(reservationDto.getIdCustomer()));
		reservation.setRoom(roomService.findRoomByNumber(reservationDto.getIdRoom()));
				
		return reservation;
	}
	
	private void updateReservationData(Reservation updatedReservation, Reservation reservation) {
		updatedReservation.setRoom(reservation.getRoom());
		updatedReservation.setStartDate(reservation.getStartDate());
		updatedReservation.setEndDate(reservation.getEndDate());
	}
}
