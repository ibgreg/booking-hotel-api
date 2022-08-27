package com.ibgregorio.bookinghotel.services.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

	/**
	 * Calculates the amount of days between start date and end date
	 * @param start - Start Date
	 * @param end - End date
	 * @return amount of days between start and end
	 */
	public static Long getAmountDaysBetweenDates(LocalDateTime start, LocalDateTime end) {
		try {
			return ChronoUnit.DAYS.between(start, end);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Converts a String date to LocalDateTime type
	 * @param dateString - Date in String format
	 * @return the converted date in LocalDateTime data type
	 */
	public static LocalDateTime parseDateStringToLocalDateTime(String dateString) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			
			return LocalDate.parse(dateString, formatter).atStartOfDay();
		} catch (Exception e) {
			return null;
		}
	}
}
