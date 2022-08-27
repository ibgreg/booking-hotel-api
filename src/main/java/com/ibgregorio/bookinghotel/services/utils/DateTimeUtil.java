package com.ibgregorio.bookinghotel.services.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {

	public static Long getAmountDaysBetweenDates(LocalDateTime start, LocalDateTime end) {
		try {			
			return ChronoUnit.DAYS.between(start, end);
		} catch (Exception e) {
			return null;
		}
	}
}
