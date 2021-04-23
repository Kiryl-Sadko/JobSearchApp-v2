package com.epam.esm.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Component
public final class Utils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private Utils() {
    }

    public static String getStringFromDate(LocalDateTime placementDate) {
        return placementDate.format(FORMATTER);
    }

    public static LocalDateTime getCalendarFromString(String date) {
        TemporalAccessor accessor = FORMATTER.parse(date);
        return LocalDateTime.from(accessor);
    }
}
