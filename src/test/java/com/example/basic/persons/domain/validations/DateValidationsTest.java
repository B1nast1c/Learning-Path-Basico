package com.example.basic.persons.domain.validations;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateValidationsTest {

    @Test
    void testValidateDateRangesValidRange() {
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate end = LocalDate.of(2020, 12, 31);
        assertTrue(DateValidations.validateDateRanges(start, end));
    }

    @Test
    void testValidateDateRangesInvalidRange() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 1);
        assertFalse(DateValidations.validateDateRanges(start, end));
    }

    @Test
    void testValidateDateRangesInvalidYear() {
        LocalDate start = LocalDate.of(1800, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 1);
        assertFalse(DateValidations.validateDateRanges(start, end));
    }

    @Test
    void testValidateBirthDateValid() {
        LocalDate birthDate = LocalDate.of(1995, 5, 20);
        assertTrue(DateValidations.validateBirthDate(birthDate));
    }

    @Test
    void testValidateBirthDateNullDate() {
        assertFalse(DateValidations.validateBirthDate(null));
    }
}
