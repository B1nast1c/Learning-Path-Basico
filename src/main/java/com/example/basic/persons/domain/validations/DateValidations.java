package com.example.basic.persons.domain.validations;

import java.time.LocalDate;
import java.util.Optional;

public class DateValidations {
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;

    public static boolean validateDateRanges(LocalDate initDate, LocalDate endDate) {
        return Optional.ofNullable(initDate)
            .flatMap(startD -> Optional.ofNullable(endDate)
                .filter(endD -> startD.isBefore(endD) || startD.isEqual(endD))
                .filter(end -> isYearValid(startD.getYear()) && isYearValid(end.getYear())))
            .isPresent();
    }

    public static boolean validateBirthDate(LocalDate birthDate) {
        return Optional.ofNullable(birthDate)
            .filter(foundDate -> isYearValid(foundDate.getYear()))
            .isPresent();
    }

    private static boolean isYearValid(int year) {
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }
}
