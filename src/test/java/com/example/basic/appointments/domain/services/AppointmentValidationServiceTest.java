package com.example.basic.appointments.domain.services;

import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentStatus;
import com.example.basic.exceptions.throwables.DateFormatExc;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

class AppointmentValidationServiceTest {

    private AppointmentValidationService service;
    private Appointment validAppointment;

    @BeforeEach
    void setUp() {
        service = new AppointmentValidationService();

        validAppointment = Appointment.builder()
            .appointmentId("APT-001")
            .appointmentDate(LocalDateTime.of(2025, 7, 1, 10, 0))
            .appointmentSpeciality(Specializations.CARDIOLOGY.name())
            .appointmentStatus(AppointmentStatus.ACTIVE)
            .build();
    }

    @Test
    void validateDateRange_shouldPass_whenDatesAreValid() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);

        Assertions.assertDoesNotThrow(() -> service.validateDateRange(start, end));
    }

    @Test
    void validateDateRange_shouldThrow_whenStartDateAfterEndDate() {
        LocalDate start = LocalDate.of(2025, 12, 31);
        LocalDate end = LocalDate.of(2025, 1, 1);

        DateFormatExc exception = Assertions.assertThrows(DateFormatExc.class, () -> service.validateDateRange(start, end));
        Assertions.assertEquals("Invalid date range", exception.getMessage());
    }

    @Test
    void validateAppointmentDate_shouldPass_whenDateIsValid() {
        Assertions.assertDoesNotThrow(() -> service.validateAppointmentDate(validAppointment));
    }

    @Test
    void validateAppointmentDate_shouldThrow_whenYearIsInvalid() {
        validAppointment.setAppointmentDate(LocalDateTime.of(1800, 7, 1, 10, 0));

        DateFormatExc exception = Assertions.assertThrows(DateFormatExc.class, () -> service.validateAppointmentDate(validAppointment));
        Assertions.assertEquals("The appointment year must be between 1900 and 2100", exception.getMessage());
    }

    @Test
    void validateAppointmentDate_shouldThrow_whenHourIsInvalid() {
        validAppointment.setAppointmentDate(LocalDateTime.of(2025, 7, 1, 7, 0)); // Antes de las 8:00

        DateFormatExc exception = Assertions.assertThrows(DateFormatExc.class, () -> service.validateAppointmentDate(validAppointment));
        Assertions.assertEquals("The appointment must be during working hours", exception.getMessage());
    }
}
