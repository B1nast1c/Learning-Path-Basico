package com.example.basic.appointments.domain.validations;

import com.example.basic.appointments.domain.services.AppointmentValidationService;

import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentStatus;
import com.example.basic.exceptions.throwables.DateFormatExc;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentValidationServiceTest {
    private AppointmentValidationService service = new AppointmentValidationService();
    private Appointment validAppointment;

    @BeforeEach
    void setUp() {
        service = new AppointmentValidationService();

        validAppointment = Appointment.builder()
            .appointmentId("APT-TEST")
            .appointmentDate(LocalDateTime.of(2025, 7, 1, 10, 0))
            .appointmentSpeciality(Specializations.CARDIOLOGY.name())
            .appointmentStatus(AppointmentStatus.ACTIVE)
            .build();
    }

    @Test
    void validateDateRange() {
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 12, 31);

        assertDoesNotThrow(() -> service.validateDateRange(start, end));
    }

    @Test
    void validateDateRangeOrderError() {
        LocalDate start = LocalDate.of(2025, 12, 31);
        LocalDate end = LocalDate.of(2025, 1, 1);

        DateFormatExc ex = assertThrows(DateFormatExc.class, () -> service.validateDateRange(start, end));
        assertEquals("Invalid date range", ex.getMessage());
    }

    @Test
    void validateAppointmentDate() {
        assertDoesNotThrow(() -> service.validateAppointmentDate(validAppointment));
    }

    @Test
    void validateAppointmentDate_shouldThrow_whenYearIsInvalid() {
        validAppointment.setAppointmentDate(LocalDateTime.of(1800, 7, 1, 10, 0));

        DateFormatExc ex = assertThrows(DateFormatExc.class, () -> service.validateAppointmentDate(validAppointment));
        assertEquals("The appointment year must be between 1900 and 2100", ex.getMessage());
    }

    @Test
    void validateAppointmentDate_shouldThrow_whenHourIsInvalid() {
        validAppointment.setAppointmentDate(LocalDateTime.of(2025, 7, 1, 7, 0)); // Antes de las 8:00

        DateFormatExc ex = assertThrows(DateFormatExc.class, () -> service.validateAppointmentDate(validAppointment));
        assertEquals("The appointment must be during working hours", ex.getMessage());
    }
}

