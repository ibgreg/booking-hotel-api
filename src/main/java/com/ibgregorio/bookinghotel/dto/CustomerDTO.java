package com.ibgregorio.bookinghotel.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	@NotBlank(message = "Name is required")
	@Length(min = 5, max = 120, message = "Name must have 5 to 120 characters")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email")
	private String email;
	
	@NotBlank(message = "Phone Number is required")
	private String phoneNumber;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
