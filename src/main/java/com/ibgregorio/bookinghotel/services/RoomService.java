package com.ibgregorio.bookinghotel.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibgregorio.bookinghotel.entity.Room;
import com.ibgregorio.bookinghotel.repository.RoomRepository;
import com.ibgregorio.bookinghotel.services.exception.DataIntegrityException;
import com.ibgregorio.bookinghotel.services.exception.ObjectNotFoundException;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	public Room findRoomByNumber(Long roomNumber) {
		Optional<Room> room = roomRepository.findById(roomNumber);

		return room.orElseThrow(() -> new ObjectNotFoundException("Room not found! Id: " + roomNumber));
	}
	
	public Boolean checkRoomAvailabilityOnStartEndDate(Long roomNumber, LocalDateTime startDate, LocalDateTime endDate) {
		findRoomByNumber(roomNumber);
		validateDateFilters(startDate, endDate);
		
		return roomRepository.checkRoomAvailabilityOnStartEndDate(roomNumber, startDate, endDate);
	}
	
	/**
	 * Validates the provided Date parameters
	 * @param startDate
	 * @param endDate
	 */
	private void validateDateFilters(LocalDateTime startDate, LocalDateTime endDate) {
		// Checks if Start Date is null
		if (startDate == null) {
			throw new DataIntegrityException("Start date is required");
		}
		
		// Checks if End Date is null
		if (endDate == null) {
			throw new DataIntegrityException("End date is required");
		}
		
		// Checks if informed Start Date is before the current date
		if (startDate.isBefore(LocalDateTime.now())) {
			throw new DataIntegrityException("Please inform a start date greater than current date");
		}
		
		// Checks if informed End Date is before the Start date
		if (endDate.isBefore(startDate)) {
			throw new DataIntegrityException("End date must be greater than Start date");
		}
	}
}
