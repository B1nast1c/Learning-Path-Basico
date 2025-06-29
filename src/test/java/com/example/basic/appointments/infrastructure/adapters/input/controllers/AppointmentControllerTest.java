package com.example.basic.appointments.infrastructure.adapters.input.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import static org.mockito.Mockito.*;
class AppointmentControllerTest {
    @Mock
    com.example.basic.appointments.application.ports.input.cases.AppointmentRequestUseCases appointmentRequestUseCases;
    @Mock
    com.example.basic.appointments.application.ports.input.cases.AppointmentUseCases appointmentUseCases;
    @InjectMocks
    com.example.basic.appointments.infrastructure.adapters.input.controllers.AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestAppointment(){
        when(appointmentRequestUseCases.createRequest(any(com.example.basic.appointments.application.ports.input.models.RequestModel.class))).thenReturn(null);

        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentController.requestAppointment(new com.example.basic.appointments.application.ports.input.models.RequestModel("patientIdRequest", "requestSpeciality", "doctorIdRequest", "requestDate", "requestDetail"));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCancelAppointment(){
        when(appointmentUseCases.cancelAppointment(anyString())).thenReturn(null);

        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentController.cancelAppointment("appointmentId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentDetail(){
        when(appointmentUseCases.getAppointmentDetails(anyString())).thenReturn(null);

        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentController.getAppointmentDetail("appointmentId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsDetailByDoctor(){
        when(appointmentUseCases.getDoctorAppointments(anyString())).thenReturn(null);

        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentController.getAppointmentsDetailByDoctor("doctorId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsDetailByPatient(){
        when(appointmentUseCases.getPatientAppointments(anyString())).thenReturn(null);

        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentController.getAppointmentsDetailByPatient("patientId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsBetween(){
        when(appointmentUseCases.getAppointmentsIn(anyString(), anyString())).thenReturn(null);

        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentController.getAppointmentsBetween("initDate", "endDate");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsPersons(){
        when(appointmentUseCases.getSpecificAppointment(anyString(), anyString())).thenReturn(null);

        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = appointmentController.getAppointmentsPersons("patientId", "doctorId");
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme