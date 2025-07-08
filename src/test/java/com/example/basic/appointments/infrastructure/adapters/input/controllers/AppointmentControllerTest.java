package com.example.basic.appointments.infrastructure.adapters.input.controllers;

import com.example.basic.appointments.application.ports.input.cases.AppointmentRequestUseCases;
import com.example.basic.appointments.application.ports.input.cases.AppointmentUseCases;
import com.example.basic.appointments.application.ports.input.models.RequestModel;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

class AppointmentControllerTest {
    @Mock
    AppointmentRequestUseCases appointmentRequestUseCases;
    @Mock
    AppointmentUseCases appointmentUseCases;
    @InjectMocks
    AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestAppointment() {
        when(appointmentRequestUseCases.createRequest(any(RequestModel.class))).thenReturn(null);

        Mono<GenericResponse> result = appointmentController.requestAppointment(new RequestModel("patientIdRequest", "requestSpeciality", "doctorIdRequest", "requestDate", "requestDetail"));
        Assertions.assertNull(result);
    }

    @Test
    void testCancelAppointment() {
        when(appointmentUseCases.cancelAppointment(anyString())).thenReturn(null);

        Mono<GenericResponse> result = appointmentController.cancelAppointment("appointmentId");
        Assertions.assertNull(result);
    }

    @Test
    void testGetAppointmentDetail() {
        when(appointmentUseCases.getAppointmentDetails(anyString())).thenReturn(null);

        Mono<GenericResponse> result = appointmentController.getAppointmentDetail("appointmentId");
        Assertions.assertNull(result);
    }

    @Test
    void testGetAppointmentsDetailByDoctor() {
        when(appointmentUseCases.getDoctorAppointments(anyString())).thenReturn(null);

        Mono<GenericResponse> result = appointmentController.getAppointmentsDetailByDoctor("doctorId");
        Assertions.assertNull(result);
    }

    @Test
    void testGetAppointmentsDetailByPatient() {
        when(appointmentUseCases.getPatientAppointments(anyString())).thenReturn(null);

        Mono<GenericResponse> result = appointmentController.getAppointmentsDetailByPatient("patientId");
        Assertions.assertNull(result);
    }

    @Test
    void testGetAppointmentsBetween() {
        when(appointmentUseCases.getAppointmentsIn(anyString(), anyString())).thenReturn(null);

        Mono<GenericResponse> result = appointmentController.getAppointmentsBetween("initDate", "endDate");
        Assertions.assertNull(result);
    }

    @Test
    void testGetAppointmentsPersons() {
        when(appointmentUseCases.getSpecificAppointment(anyString(), anyString())).thenReturn(null);

        Mono<GenericResponse> result = appointmentController.getAppointmentsPersons("patientId", "doctorId");
        Assertions.assertNull(result);
    }
}