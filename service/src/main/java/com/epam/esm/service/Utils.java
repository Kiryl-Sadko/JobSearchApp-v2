package com.epam.esm.service;

import com.epam.esm.exception.ParseToCalendarException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public final class Utils {

    private static final Logger LOGGER = LogManager.getLogger(Utils.class);

    private Utils() {
    }

    public static String getStringFromDate(Calendar placementDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(placementDate.getTime());
    }

    public static Calendar getCalendarFromString(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date formattedDate;
        try {
            formattedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            LOGGER.error("invalid date format has entered");
            throw new ParseToCalendarException("invalid date format has entered", e);
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(formattedDate);
        return calendar;
    }
}
