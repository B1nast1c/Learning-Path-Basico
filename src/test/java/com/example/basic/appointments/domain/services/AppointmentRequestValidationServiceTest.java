package com.example.basic.appointments.domain.services;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

class AppointmentRequestValidationServiceTest {
    AppointmentRequestValidationService appointmentRequestValidationService = new AppointmentRequestValidationService();

    @Test
    void testValidate() {
        Mono<AppointmentRequest> result = appointmentRequestValidationService.validate(null, null, null, null);
        Assertions.assertEquals(null, result);
    }
}