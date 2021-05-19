package com.epam.esm.service;

import com.epam.esm.dto.Dto;
import com.epam.esm.exception.InvalidDtoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Set;

@Component
public final class Utils {

    private static final Logger LOGGER = LogManager.getLogger(Utils.class);
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

    public static void validate(Dto dto, Validator validator) {
        Set<ConstraintViolation<Dto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringBuilder causes = new StringBuilder();
            violations.stream().iterator().forEachRemaining(violation -> causes
                    .append("'")
                    .append(violation.getPropertyPath().toString())
                    .append("' ")
                    .append(violation.getMessage())
                    .append(";"));
            String message = MessageFormat.format("The dto is invalid, check causes: {0}", causes);
            LOGGER.error(message);
            throw new InvalidDtoException(message);
        }
    }
}
