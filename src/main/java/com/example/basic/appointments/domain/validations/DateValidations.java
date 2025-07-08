package com.example.basic.appointments.domain.validations;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Clase que contiene métodos para validar fechas y horarios relacionados con citas médicas.
 * Se asegura de que las fechas estén en rangos válidos y que las horas estén dentro del horario laboral.
 */
public class DateValidations {

    // Año mínimo y máximo permitido para una cita
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;

    // Horario laboral permitido para citas (de 8:00 a 19:00)
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(19, 0);

    /**
     * Verifica que dos fechas no sean iguales y que haya al menos una hora de diferencia entre ellas.
     *
     * @param requestedDate Fecha solicitada para la nueva cita.
     * @param registeredDate Fecha de una cita ya registrada.
     * @return true si hay al menos una hora de diferencia y no son iguales.
     */
    public static boolean validateDates(LocalDateTime requestedDate, LocalDateTime registeredDate) {
        return !requestedDate.isEqual(registeredDate) &&
            Duration.between(requestedDate, registeredDate).toHours() >= 1;
    }

    /**
     * Verifica que la hora de la cita esté dentro del horario laboral permitido.
     *
     * @param requestedDate Fecha y hora solicitada.
     * @return true si está entre las 8:00 y las 19:00.
     */
    public static boolean validateHour(LocalDateTime requestedDate) {
        LocalTime requestedTime = requestedDate.toLocalTime();
        return !requestedTime.isBefore(START_TIME) && !requestedTime.isAfter(END_TIME);
    }

    /**
     * Verifica que el año esté dentro del rango permitido.
     *
     * @param year Año a validar.
     * @return true si está entre 1900 y 2100.
     */
    public static boolean isYearValid(int year) {
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    /**
     * Verifica que el rango de fechas sea válido y que los años estén dentro del rango permitido.
     *
     * @param initDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @return true si el rango es válido y los años están dentro del rango.
     */
    public static boolean validateDateRanges(LocalDate initDate, LocalDate endDate) {
        return Optional.ofNullable(initDate)
            .flatMap(startD -> Optional.ofNullable(endDate)
                .filter(endD -> startD.isBefore(endD) || startD.isEqual(endD))
                .filter(end -> isYearValid(startD.getYear()) && isYearValid(end.getYear())))
            .isPresent();
    }
}