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
	
	private void validateDateFilters(LocalDateTime startDate, LocalDateTime endDate) {	
		if (startDate == null) {
			throw new DataIntegrityException("Start date is required");
		}
		
		if (endDate == null) {
			throw new DataIntegrityException("End date is required");
		}
		
		if (startDate.isBefore(LocalDateTime.now())) {
			throw new DataIntegrityException("Please inform a start date greater than current date");
		}
		
		if (endDate.isBefore(startDate)) {
			throw new DataIntegrityException("End date must be greater than Start date");
		}
	}
}
