package com.example.basic.appointments.application.utils;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentStatus;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

class AppointmentMapperTest {
    ModelMapper modelMapper;
    AppointmentMapper appointmentMapper;
    Appointment appointment;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        appointmentMapper = new AppointmentMapper(new ModelMapper());

        appointment = Appointment.builder()
            .appointmentId("APT-TEST")
            .requestId("REQ-TEST")
            .patientId("PATIENT-ID")
            .patientFullName("PATIENT")
            .doctorId("DOCTOR-ID")
            .doctorFullName("DOCTOR")
            .appointmentDate(LocalDateTime.now())
            .appointmentSpeciality(String.valueOf(Specializations.OBSTETRICS))
            .appointmentStatus(AppointmentStatus.ACTIVE)
            .build();
    }

    @Test
    void testMapAppointmentToResponse() {
        AppointmentsResponse result = appointmentMapper.mapAppointmentToResponse(appointment);

        Assertions.assertEquals(appointment.getRequestId(), result.getRequestId());
        Assertions.assertEquals(appointment.getPatientFullName(), result.getPatientFullName());
        Assertions.assertEquals(appointment.getDoctorFullName(), result.getDoctorFullName());
    }

    @Test
    void testMapAppointmentsToResponse() {
        List<AppointmentsResponse> result = appointmentMapper.mapAppointmentsToResponse(List.of(appointment));
        AppointmentsResponse gotResult = result.get(0);

        Assertions.assertEquals(appointment.getRequestId(), gotResult.getRequestId());
        Assertions.assertEquals(appointment.getPatientFullName(), gotResult.getPatientFullName());
        Assertions.assertEquals(appointment.getDoctorFullName(), gotResult.getDoctorFullName());
    }
}