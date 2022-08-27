package com.ibgregorio.bookinghotel.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ibgregorio.bookinghotel.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

	@Transactional(readOnly = true)
	@Query("select count(r) > 0 from Room r right join r.reservation reserv "
			+ "where r.roomNumber = :roomNumber "
			+ "and (r.reservation is null or (r.reservation is not null "
			+ "and (:chosenStartDate between reserv.startDate and reserv.endDate "
			+ "or :chosenEndDate between reserv.startDate and reserv.endDate)))")
	Boolean checkRoomAvailabilityOnStartEndDate(@Param("roomNumber") Long roomNumber, 
			@Param("chosenStartDate") LocalDateTime chosenStartDate,
			@Param("chosenEndDate") LocalDateTime chosenEndDate);
}
