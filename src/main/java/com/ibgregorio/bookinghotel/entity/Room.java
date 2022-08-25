package com.ibgregorio.bookinghotel.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Room implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long roomNumber;
	
	private Double price;
	
	private String category;
	
	private Integer numberOfBeds;
	
	private boolean balconyAvailable;
	
	@OneToOne(mappedBy = "room")
	private Reservation reservation;
	
	public Room() {
	}

	public Room(Long roomNumber, Double price, String category, Integer numberOfBeds, boolean balconyAvailable) {
		super();
		this.roomNumber = roomNumber;
		this.price = price;
		this.category = category;
		this.numberOfBeds = numberOfBeds;
		this.balconyAvailable = balconyAvailable;
	}

	public Long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getNumberOfBeds() {
		return numberOfBeds;
	}

	public void setNumberOfBeds(Integer numberOfBeds) {
		this.numberOfBeds = numberOfBeds;
	}

	public boolean isBalconyAvailable() {
		return balconyAvailable;
	}

	public void setBalconyAvailable(boolean balconyAvailable) {
		this.balconyAvailable = balconyAvailable;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roomNumber == null) ? 0 : roomNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (roomNumber == null) {
			if (other.roomNumber != null)
				return false;
		} else if (!roomNumber.equals(other.roomNumber))
			return false;
		return true;
	}

}
