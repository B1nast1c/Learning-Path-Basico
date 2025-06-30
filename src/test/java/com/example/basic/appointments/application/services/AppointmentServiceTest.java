package com.example.basic.appointments.application.services;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import com.example.basic.appointments.application.ports.output.repository.AppointmentRepositoryInterface;
import com.example.basic.appointments.application.utils.AppointmentMapper;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    @Mock
    AppointmentRepositoryInterface repository;
    @Mock
    AppointmentMapper mapper;
    @InjectMocks
    AppointmentService service;
    Appointment testAppointment;
    AppointmentsResponse appointmentsResponse;

    @BeforeEach
    void setUp() {
        testAppointment = Appointment.builder()
            .appointmentId("APT-TEST")
            .requestId("REQ-TEST")
            .doctorId("DOCTOR-ID")
            .doctorFullName("DOCTOR-NAME")
            .patientId("PATIENT-ID")
            .patientFullName("PATIENT-NAME")
            .appointmentDate(LocalDateTime.of(2020, 12, 12, 12, 0, 0))
            .appointmentSpeciality("CARDIOLOGY")
            .appointmentStatus(AppointmentStatus.ACTIVE)
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
    void testCreateAppointment() {
        Mockito.when(repository.saveAppointment(testAppointment)).thenReturn(Mono.just(testAppointment));

        AppointmentsResponse result = service.createAppointment(testAppointment).block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testAppointment.getRequestId(), result.getRequestId());
        Assertions.assertEquals(testAppointment.getPatientId(), result.getPatientIdRequest());
        Assertions.assertEquals(testAppointment.getDoctorId(), result.getDoctorIdRequest());Assertions.assertNotNull(result);
        Assertions.assertEquals(testAppointment.getRequestId(), result.getRequestId());
        Assertions.assertEquals(testAppointment.getPatientId(), result.getPatientIdRequest());
        Assertions.assertEquals(testAppointment.getDoctorId(), result.getDoctorIdRequest());
    }

    @Test
    void testGetAppointmentDetails() {
        Mockito.when(repository.getAppointmentById("APT-TEST"))
            .thenReturn(Mono.just(testAppointment));
        Mockito.when(mapper.mapAppointmentToResponse(Mockito.any(Appointment.class)))
            .thenReturn(appointmentsResponse);

        GenericResponse result = service.getAppointmentDetails("APT-TEST").block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testAppointment.getRequestId(), result.getData().get(0).getRequestId());
        Assertions.assertEquals(testAppointment.getPatientId(), result.getData().get(0).getPatientIdRequest());
        Assertions.assertEquals(testAppointment.getDoctorId(), result.getData().get(0).getDoctorIdRequest());
    }

    @Test
    void testGetPatientAppointments() {
        Mockito.when(repository.getAppointmentsByPatient("PATIENT-ID"))
            .thenReturn(Flux.just(testAppointment));
        Mockito.when(mapper.mapAppointmentsToResponse(Mockito.anyList()))
            .thenReturn(List.of(appointmentsResponse));

        GenericResponse result = service.getPatientAppointments("PATIENT-ID").block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testAppointment.getRequestId(), result.getData().get(0).getRequestId());
        Assertions.assertEquals(testAppointment.getPatientId(), result.getData().get(0).getPatientIdRequest());
        Assertions.assertEquals(testAppointment.getDoctorId(), result.getData().get(0).getDoctorIdRequest());
    }

    @Test
    void testGetDoctorAppointments() {
        Mockito.when(repository.getAppointmentsByDoctor("DOCTOR-ID"))
            .thenReturn(Flux.just(testAppointment));
        Mockito.when(mapper.mapAppointmentsToResponse(Mockito.anyList()))
            .thenReturn(List.of(appointmentsResponse));

        GenericResponse result = service.getDoctorAppointments("DOCTOR-ID").block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testAppointment.getRequestId(), result.getData().get(0).getRequestId());
        Assertions.assertEquals(testAppointment.getPatientId(), result.getData().get(0).getPatientIdRequest());
        Assertions.assertEquals(testAppointment.getDoctorId(), result.getData().get(0).getDoctorIdRequest());
    }

    @Test
    void testGetAppointmentsIn() {
        Mockito.when(repository.getAppointmentsByDateRange("2024-01-01", "2024-01-31"))
            .thenReturn(Flux.just(testAppointment));
        Mockito.when(mapper.mapAppointmentsToResponse(Mockito.anyList()))
            .thenReturn(List.of(appointmentsResponse));

        GenericResponse result = service.getAppointmentsIn("2024-01-01", "2024-01-31").block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testAppointment.getRequestId(), result.getData().get(0).getRequestId());
        Assertions.assertEquals(testAppointment.getPatientId(), result.getData().get(0).getPatientIdRequest());
        Assertions.assertEquals(testAppointment.getDoctorId(), result.getData().get(0).getDoctorIdRequest());
    }

    @Test
    void testGetSpecificAppointment() {
        Mockito.when(repository.getAppointmentsByPersons("PATIENT-ID", "DOCTOR-ID"))
            .thenReturn(Flux.just(testAppointment));
        Mockito.when(mapper.mapAppointmentsToResponse(Mockito.anyList()))
            .thenReturn(List.of(appointmentsResponse));

        GenericResponse result = service.getSpecificAppointment("PATIENT-ID", "DOCTOR-ID").block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(testAppointment.getRequestId(), result.getData().get(0).getRequestId());
        Assertions.assertEquals(testAppointment.getPatientId(), result.getData().get(0).getPatientIdRequest());
        Assertions.assertEquals(testAppointment.getDoctorId(), result.getData().get(0).getDoctorIdRequest());
    }

    @Test
    void testCancelAppointmentReturnsNull() {
        Mono<GenericResponse> result = service.cancelAppointment("APT-TEST");
        Assertions.assertNull(result);
    }
}
