package com.example.basic.appointments.application.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class AppointmentRequestMapperTest {
    
    com.example.basic.appointments.application.utils.AppointmentRequestMapper appointmentRequestMapper = new com.example.basic.appointments.application.utils.AppointmentRequestMapper(new org.modelmapper.ModelMapper());

    @Test
    void testMapAppointmentRequestToResponse(){
        com.example.basic.appointments.application.ports.output.models.AppointmentsResponse result = appointmentRequestMapper.mapAppointmentRequestToResponse(null);
        Assertions.assertEquals(new com.example.basic.appointments.application.ports.output.models.AppointmentsResponse("requestId", java.time.LocalDateTime.of(2025, java.time.Month.JUNE, 30, 11, 27, 30), "patientIdRequest", "patientFullName", "doctorIdRequest", "doctorFullName", "requestDetail"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme