package com.example.basic.appointments.domain.validations;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class DateValidations {
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(19, 0);

    public static boolean validateDates(LocalDateTime requestedDate, LocalDateTime registeredDate) {
        return !requestedDate.isEqual(registeredDate) && Duration.between(requestedDate, registeredDate).toHours() >= 1;
    }

    public static boolean validateHour(LocalDateTime requestedDate) {
        LocalTime requestedTime = requestedDate.toLocalTime();
        return !requestedTime.isBefore(START_TIME) && !requestedTime.isAfter(END_TIME);
    }

    public static boolean isYearValid(int year) {
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    public static boolean validateDateRanges(LocalDate initDate, LocalDate endDate) {
        return Optional.ofNullable(initDate)
            .flatMap(startD -> Optional.ofNullable(endDate)
                .filter(endD -> startD.isBefore(endD) || startD.isEqual(endD))
                .filter(end -> isYearValid(startD.getYear()) && isYearValid(end.getYear())))
            .isPresent();
    }
}
