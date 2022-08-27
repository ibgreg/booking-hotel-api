package com.ibgregorio.bookinghotel.services.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

	public static Long getAmountDaysBetweenDates(LocalDateTime start, LocalDateTime end) {
		try {
			return ChronoUnit.DAYS.between(start, end);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static LocalDateTime parseDateStringToLocalDateTime(String dateString) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			
			return LocalDate.parse(dateString, formatter).atStartOfDay();
		} catch (Exception e) {
			return null;
		}
	}
}
