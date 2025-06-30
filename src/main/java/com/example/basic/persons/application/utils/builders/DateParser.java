package com.example.basic.persons.application.utils.builders;

import com.example.basic.exceptions.throwables.DateFormatExc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilidad para convertir cadenas de texto en fechas.
 * Esta clase se asegura de que las fechas ingresadas tengan el formato correcto
 * y lanza una excepción personalizada si el formato es inválido.
 */
public class DateParser {
    // Formato esperado: dd/MM/YYYY
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Convierte una cadena de texto en un objeto LocalDate.
     *
     * @param strDate Fecha en formato de texto (dd/MM/yyyy).
     * @return La fecha convertida como LocalDate.
     * @throws DateFormatExc si el formato de la fecha es incorrecto.
     */
    public static LocalDate parseDate(String strDate) {
        try {
            return LocalDate.parse(strDate, formatter);
        } catch (DateTimeParseException ex) {
            throw new DateFormatExc("Invalid date format, must be dd/MM/YYYY");
        }
    }
}
