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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/rooms")
public class RoomResource {

	@Autowired
	private RoomService roomService;
	
	@Operation(summary = "Checks selected room availability at specific period")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Returns a message telling current room availability status"),
			  @ApiResponse(responseCode = "400", description = "Date validation error or missing required query parameter", 
			  	content = @Content(mediaType = "application/json")), 
			  @ApiResponse(responseCode = "404", description = "Room validation error", 
			  	content = @Content(mediaType = "application/json")) })
	@GetMapping
	public ResponseEntity<String> checkRoomAvailabilityOnStartEndDate(
			@RequestParam(value = "roomNumber", required = true) @NotBlank String roomNumber,
			@RequestParam(value = "startDate", required = true) @NotBlank String startDate,
			@RequestParam(value = "endDate", required = true) @NotBlank String endDate) {
		Long parsedRoomNumber = Long.parseLong(roomNumber);
		LocalDateTime parsedStartDate = DateTimeUtil.parseDateStringToLocalDateTime(startDate);
		LocalDateTime parsedEndDate = DateTimeUtil.parseDateStringToLocalDateTime(endDate);
		
		
		Boolean isRoomReserved = roomService.checkRoomAvailabilityOnStartEndDate(parsedRoomNumber, parsedStartDate, parsedEndDate);
		String returnMsg = isRoomReserved ? "Room unavailable on the informed period" : "Room available!";
		
		return ResponseEntity.ok().body(returnMsg);
	}
}
