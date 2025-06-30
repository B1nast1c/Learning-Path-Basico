package com.example.basic.appointments.domain.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class DateValidationsTest {

    @Test
    void testValidateDates(){
        boolean result = DateValidations.validateDates(java.time.LocalDateTime.of(2025, java.time.Month.JUNE, 30, 1, 2, 29), java.time.LocalDateTime.of(2025, java.time.Month.JUNE, 30, 1, 2, 29));
        Assertions.assertEquals(true, result);
    }

    @Test
    void testValidateHour(){
        boolean result = DateValidations.validateHour(java.time.LocalDateTime.of(2025, java.time.Month.JUNE, 30, 1, 2, 29));
        Assertions.assertEquals(true, result);
    }

    @Test
    void testIsYearValid(){
        boolean result = DateValidations.isYearValid(0);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testValidateDateRanges(){
        boolean result = DateValidations.validateDateRanges(java.time.LocalDate.of(2025, java.time.Month.JUNE, 30), java.time.LocalDate.of(2025, java.time.Month.JUNE, 30));
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme