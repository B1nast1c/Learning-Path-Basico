package com.example.basic.persons.application.utils.builders;

import com.example.basic.exceptions.throwables.DateFormatExc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateParserTest {

    @Test
    void testParseDate() {
        LocalDate result = DateParser.parseDate("29/06/2025");

        Assertions.assertEquals(LocalDate.of(2025, Month.JUNE, 29), result);
    }

    @Test
    void testParseDateInvalidDate() {
        DateFormatExc exception = assertThrows(DateFormatExc.class, () -> DateParser.parseDate("INVALID"));

        assertEquals("Invalid date format, must be dd/MM/YYYY", exception.getMessage());
    }
}