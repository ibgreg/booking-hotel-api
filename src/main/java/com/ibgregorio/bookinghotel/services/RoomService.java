package com.ibgregorio.bookinghotel.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibgregorio.bookinghotel.entity.Room;
import com.ibgregorio.bookinghotel.repository.RoomRepository;
import com.ibgregorio.bookinghotel.services.exception.ObjectNotFoundException;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	public Room findRoomByNumber(Long roomNumber) {
		Optional<Room> room = roomRepository.findById(roomNumber);

		return room.orElseThrow(() -> new ObjectNotFoundException("Room not found! Id: " + roomNumber));
	}
}
