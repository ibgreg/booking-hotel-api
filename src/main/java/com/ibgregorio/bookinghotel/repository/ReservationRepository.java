package com.ibgregorio.bookinghotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ibgregorio.bookinghotel.entity.Customer;
import com.ibgregorio.bookinghotel.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Transactional(readOnly = true)
	List<Reservation> findByCustomer(Customer customer);
}
