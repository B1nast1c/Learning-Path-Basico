package com.example.basic.persons.domain.services;

import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PatientValidationServiceTest {

    private final PatientValidationService service = new PatientValidationService();
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = Patient.builder()
            .personID("22222222")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.PATIENT)
            .isActive(true)
            .birthDate(LocalDate.now())
            .build();
    }

    @Test
    void testValidateValidPatient() {
        assertDoesNotThrow(() -> service.validate(patient));
    }

    @Test
    void testValidateInvalidID() {
        patient.setPersonID("ABC123");

        SpecialityExc exception = assertThrows(SpecialityExc.class, () -> service.validate(patient));
        assertEquals("Invalid patient ID format", exception.getMessage());
    }

    @Test
    void testValidateInvalidBirthDate() {
        patient.setPersonID("22222222");
        patient.setBirthDate(LocalDate.of(1800, 1, 1));

        SpecialityExc exception = assertThrows(SpecialityExc.class, () -> service.validate(patient));
        assertEquals("Invalid birth date", exception.getMessage());
    }

    @Test
    void testValidateDateRangeValid() {
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 1);

        assertTrue(service.validateDateRange(start, end));
    }

    @Test
    void testValidateDateRangeInvalid() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 1);

        assertFalse(service.validateDateRange(start, end));
    }
}
