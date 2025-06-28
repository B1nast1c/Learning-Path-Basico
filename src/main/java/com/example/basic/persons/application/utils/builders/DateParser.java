package com.example.basic.persons.application.utils.builders;

import com.example.basic.exceptions.throwables.DateFormatExc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate parseDate(String strDate) {
        try {
            return LocalDate.parse(strDate, formatter);
        }
        catch (DateTimeParseException ex) {
           throw new DateFormatExc("Invalid date format, must be dd/MM/YYYY");
        }
    }
}

