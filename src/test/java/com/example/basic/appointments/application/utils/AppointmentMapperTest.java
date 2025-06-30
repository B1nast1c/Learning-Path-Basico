package com.example.basic.appointments.application.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class AppointmentMapperTest {
    
    com.example.basic.appointments.application.utils.AppointmentMapper appointmentMapper = new com.example.basic.appointments.application.utils.AppointmentMapper(new org.modelmapper.ModelMapper());

    @Test
    void testMapAppointmentToResponse(){
        com.example.basic.appointments.application.ports.output.models.AppointmentsResponse result = appointmentMapper.mapAppointmentToResponse(null);
        Assertions.assertEquals(new com.example.basic.appointments.application.ports.output.models.AppointmentsResponse("requestId", java.time.LocalDateTime.of(2025, java.time.Month.JUNE, 30, 11, 27, 16), "patientIdRequest", "patientFullName", "doctorIdRequest", "doctorFullName", "requestDetail"), result);
    }

    @Test
    void testMapAppointmentsToResponse(){
        java.util.List<com.example.basic.appointments.application.ports.output.models.AppointmentsResponse> result = appointmentMapper.mapAppointmentsToResponse(java.util.List.of(null));
        Assertions.assertEquals(java.util.List.of(new com.example.basic.appointments.application.ports.output.models.AppointmentsResponse("requestId", java.time.LocalDateTime.of(2025, java.time.Month.JUNE, 30, 11, 27, 16), "patientIdRequest", "patientFullName", "doctorIdRequest", "doctorFullName", "requestDetail")), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme