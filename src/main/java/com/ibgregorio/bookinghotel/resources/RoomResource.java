package com.ibgregorio.bookinghotel.resources;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibgregorio.bookinghotel.services.RoomService;
import com.ibgregorio.bookinghotel.services.utils.DateTimeUtil;

@RestController
@RequestMapping(value = "/rooms")
public class RoomResource {

	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public ResponseEntity<String> checkRoomAvailabilityOnStartEndDate(
			@RequestParam(value = "roomNumber", required = true) @NotBlank String roomNumber,
			@RequestParam(value = "startDate", required = true) @NotBlank String startDate,
			@RequestParam(value = "endDate", required = true) @NotBlank String endDate) {
		Long parsedRoomNumber = Long.parseLong(roomNumber);
		LocalDateTime parsedStartDate = DateTimeUtil.parseDateStringToLocalDateTime(startDate);
		LocalDateTime parsedEndDate = DateTimeUtil.parseDateStringToLocalDateTime(endDate);
		
		
		Boolean isRoomReserved = roomService.checkRoomAvailabilityOnStartEndDate(parsedRoomNumber, parsedStartDate, parsedEndDate);
		String returnMsg = isRoomReserved ? "Room unavailable in given period" : "Room available!";
		
		return ResponseEntity.ok().body(returnMsg);
	}
}
