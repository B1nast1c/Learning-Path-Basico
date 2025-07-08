package com.example.basic.persons.domain.validations;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Clase utilitaria para validar fechas relacionadas con pacientes u otras entidades.
 */
public class DateValidations {

    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;

    /**
     * Valida que un rango de fechas sea correcto.
     * Verifica que ambas fechas no sean nulas, que la fecha de inicio sea anterior o igual a la fecha de fin,
     * y que los años estén dentro del rango permitido.
     *
     * @param initDate Fecha de inicio.
     * @param endDate Fecha de fin.
     * @return true si el rango es válido, false en caso contrario.
     */
    public static boolean validateDateRanges(LocalDate initDate, LocalDate endDate) {
        return Optional.ofNullable(initDate)
            .flatMap(startD -> Optional.ofNullable(endDate)
                .filter(endD -> startD.isBefore(endD) || startD.isEqual(endD))
                .filter(end -> isYearValid(startD.getYear()) && isYearValid(end.getYear())))
            .isPresent();
    }

    /**
     * Valida que una fecha de nacimiento sea válida.
     * La fecha no debe ser nula y su año debe estar dentro del rango permitido.
     *
     * @param birthDate Fecha de nacimiento a validar.
     * @return true si la fecha es válida, false en caso contrario.
     */
    public static boolean validateBirthDate(LocalDate birthDate) {
        return Optional.ofNullable(birthDate)
            .filter(foundDate -> isYearValid(foundDate.getYear()))
            .isPresent();
    }

    /**
     * Verifica si un año está dentro del rango permitido.
     *
     * @param year Año a validar.
     * @return true si el año está entre 1900 y 2100, inclusive.
     */
    private static boolean isYearValid(int year) {
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }
}
