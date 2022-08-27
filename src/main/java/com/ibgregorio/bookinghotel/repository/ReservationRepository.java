package com.ibgregorio.bookinghotel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ibgregorio.bookinghotel.entity.Customer;
import com.ibgregorio.bookinghotel.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Transactional(readOnly = true)
	List<Reservation> findByCustomer(Customer customer);

	@Transactional(readOnly = true)
	@Query("select count(reserv) > 0 from Reservation reserv join reserv.room r "
			+ "where r.roomNumber = :roomNumber "
			+ "and (:chosenStartDate between reserv.startDate and reserv.endDate "
			+ "or :chosenEndDate between reserv.startDate and reserv.endDate)")
	Boolean existsActiveReservationByRoomBetweenStartEndDates(@Param("roomNumber") Long roomNumber, 
			@Param("chosenStartDate") LocalDateTime chosenStartDate,
			@Param("chosenEndDate") LocalDateTime chosenEndDate);
	

}
