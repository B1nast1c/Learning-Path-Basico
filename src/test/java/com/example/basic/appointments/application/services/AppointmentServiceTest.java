package com.example.basic.appointments.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class AppointmentServiceTest {
    
    
    com.example.basic.appointments.application.services.AppointmentService appointmentService = new com.example.basic.appointments.application.services.AppointmentService(new com.example.basic.appointments.infrastructure.adapters.output.repositories.AppointmentsRepository(null, new com.example.basic.appointments.domain.services.AppointmentValidationService()), new com.example.basic.appointments.application.utils.AppointmentMapper(new org.modelmapper.ModelMapper()));

    @Test
    void testCreateAppointment(){
        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.AppointmentsResponse> result = appointmentService.createAppointment(null);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCancelAppointment(){
        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentService.cancelAppointment("appointmentId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentDetails(){
        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentService.getAppointmentDetails("appointmentId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetPatientAppointments(){
        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentService.getPatientAppointments("patientId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetDoctorAppointments(){
        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentService.getDoctorAppointments("doctorId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsIn(){
        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentService.getAppointmentsIn("initDate", "endDate");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetSpecificAppointment(){
        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentService.getSpecificAppointment("patientId", "doctorId");
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme