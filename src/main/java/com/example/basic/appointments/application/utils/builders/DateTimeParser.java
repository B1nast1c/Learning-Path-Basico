package com.example.basic.appointments.application.utils.builders;

import com.example.basic.exceptions.throwables.DateFormatExc;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.time.LocalDateTime;

public class DateTimeParser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDateTime parseDateTime(String strDateTime) {
        try {
            return LocalDateTime.parse(strDateTime, formatter);
        } catch (DateTimeParseException ex) {
            throw new DateFormatExc("Invalid datetime format. Expected format: dd/MM/yyyy HH:mm");
        }
    }
}


