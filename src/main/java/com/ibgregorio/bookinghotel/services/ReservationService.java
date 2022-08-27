package com.ibgregorio.bookinghotel.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibgregorio.bookinghotel.dto.ReservationDTO;
import com.ibgregorio.bookinghotel.entity.Customer;
import com.ibgregorio.bookinghotel.entity.Reservation;
import com.ibgregorio.bookinghotel.repository.ReservationRepository;
import com.ibgregorio.bookinghotel.services.exception.DataIntegrityException;
import com.ibgregorio.bookinghotel.services.exception.ObjectNotFoundException;
import com.ibgregorio.bookinghotel.services.utils.DateTimeUtil;

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
	
	public List<Reservation> findReservationsByCustomer(Long idCustomer) {
		Customer selectedCustomer = customerService.findCustomerById(idCustomer);
		
		return reservationRepository.findByCustomer(selectedCustomer);
	}
	
	@Transactional
	public Reservation placeReservation(Reservation reservation) {
		validateReservation(reservation);
		
		return reservationRepository.save(reservation);
	}
	
	@Transactional
	public Reservation modifyReservation(Reservation reservation) {
		Reservation modifiedReservation = findReservationById(reservation.getId());
		validateReservation(reservation);
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
				reservationDto.getStartDate().atStartOfDay(), 
				reservationDto.getEndDate().atTime(23, 59, 59));
		
		reservation.setCustomer(customerService.findCustomerById(reservationDto.getIdCustomer()));
		reservation.setRoom(roomService.findRoomByNumber(reservationDto.getIdRoom()));
				
		return reservation;
	}
	
	private void updateReservationData(Reservation updatedReservation, Reservation reservation) {
		updatedReservation.setRoom(reservation.getRoom());
		updatedReservation.setStartDate(reservation.getStartDate());
		updatedReservation.setEndDate(reservation.getEndDate());
	}
	
	private void validateReservation(Reservation reservation) {		
		if (reservation.getStartDate().isBefore(LocalDateTime.now())) {
			throw new DataIntegrityException("Please inform a start date greater than current date");
		}
		
		if (reservation.getEndDate().isBefore(reservation.getStartDate())) {
			throw new DataIntegrityException("End date must be greater than Start date");
		}
		
		if (DateTimeUtil.getAmountDaysBetweenDates(LocalDateTime.now(), reservation.getStartDate()) > 30) {
			throw new DataIntegrityException("Your reservation start date must be less than 30 days in advance");
		}
		
		if (DateTimeUtil.getAmountDaysBetweenDates(reservation.getStartDate(), reservation.getEndDate()) > 3) {
			throw new DataIntegrityException("Your stay can't be longer than 3 days");
		}
		
		if (roomService.checkRoomAvailabilityOnStartEndDate(reservation.getRoom().getRoomNumber(), reservation.getStartDate(), reservation.getEndDate())) {
			throw new DataIntegrityException("The selected room is already reserved on informed period");
		}
	}

}
