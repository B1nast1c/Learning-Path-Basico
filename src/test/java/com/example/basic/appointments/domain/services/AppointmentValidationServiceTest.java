package com.example.basic.appointments.domain.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class AppointmentValidationServiceTest {
    com.example.basic.appointments.domain.services.AppointmentValidationService appointmentValidationService = new com.example.basic.appointments.domain.services.AppointmentValidationService();

    @Test
    void testValidateDateRange(){
        appointmentValidationService.validateDateRange(java.time.LocalDate.of(2025, java.time.Month.JUNE, 30), java.time.LocalDate.of(2025, java.time.Month.JUNE, 30));
    }

    @Test
    void testValidateAppointmentDate(){
        appointmentValidationService.validateAppointmentDate(null);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme