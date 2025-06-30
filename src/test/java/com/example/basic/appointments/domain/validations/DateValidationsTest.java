package com.example.basic.appointments.domain.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

class DateValidationsTest {

    @Test
    void validateDates() {
        LocalDateTime requested = LocalDateTime.of(2025, 6, 29, 10, 0);
        LocalDateTime registered = LocalDateTime.of(2025, 6, 29, 11, 0);

        Assertions.assertTrue(DateValidations.validateDates(requested, registered));
    }

    @Test
    void validateHourWorkingHours() {
        LocalDateTime date = LocalDateTime.of(2025, 6, 29, 9, 0);

        Assertions.assertTrue(DateValidations.validateHour(date));
    }

    @Test
    void validateHourWorkingHoursError() {
        LocalDateTime date = LocalDateTime.of(2025, 6, 29, 7, 59);

        Assertions.assertFalse(DateValidations.validateHour(date));
    }

    @Test
    void isYearValid() {
        Assertions.assertTrue(DateValidations.isYearValid(2000));
    }

    @Test
    void isYearValidError() {
        Assertions.assertFalse(DateValidations.isYearValid(3000));
    }

    @Test
    void validateDateRangesDatesOrder() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);

        Assertions.assertTrue(DateValidations.validateDateRanges(start, end));
    }

    @Test
    void validateDateRangesOrderError() {
        LocalDate start = LocalDate.of(2025, 12, 31);
        LocalDate end = LocalDate.of(2025, 1, 1);

        Assertions.assertFalse(DateValidations.validateDateRanges(start, end));
    }

    @Test
    void validateDateRangesInvalidYear() {
        LocalDate start = LocalDate.of(1800, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 1);

        Assertions.assertFalse(DateValidations.validateDateRanges(start, end));
    }
}