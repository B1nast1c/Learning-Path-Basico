package com.example.basic.appointments.application.utils;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

class AppointmentRequestMapperTest {
    ModelMapper modelMapper;
    AppointmentRequestMapper requestMapper;
    AppointmentRequest appointmentRequest;
    AppointmentsResponse appointmentsResponse;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        requestMapper = new AppointmentRequestMapper(new ModelMapper());

        appointmentRequest = AppointmentRequest.builder()
            .requestId("REQ-TEST")
            .patientIdRequest("PATIENT-ID")
            .patientFullName("NAME SURNAME")
            .requestSpeciality(Specializations.NEUROLOGY)
            .doctorIdRequest("DOCTOR-ID")
            .doctorFullName("NAME SURNAME")
            .requestDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
            .isValid(true)
            .requestDetail("")
            .build();

        appointmentsResponse = AppointmentsResponse.builder()
            .requestId("REQ-TEST")
            .requestDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
            .patientIdRequest("PATIENT-ID")
            .patientFullName("NAME SURNAME")
            .doctorIdRequest("DOCTOR-ID")
            .doctorFullName("NAME SURNAME")
            .requestDetail("")
            .build();
    }

    @Test
    void testMapAppointmentRequestToResponse() {
        AppointmentsResponse result = requestMapper.mapAppointmentRequestToResponse(appointmentRequest);

        Assertions.assertEquals(appointmentsResponse.getRequestId(), result.getRequestId());
        Assertions.assertEquals(appointmentsResponse.getDoctorIdRequest(), result.getDoctorIdRequest());
        Assertions.assertEquals(appointmentsResponse.getPatientIdRequest(), result.getPatientIdRequest());

    }
}