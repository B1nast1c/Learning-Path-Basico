package com.example.basic.appointments.application.services;

import com.example.basic.appointments.application.ports.input.cases.AppointmentUseCases;
import com.example.basic.appointments.application.ports.input.models.RequestModel;
import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import com.example.basic.appointments.application.ports.output.repository.RequestRepositoryInterface;
import com.example.basic.appointments.application.utils.AppointmentRequestMapper;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {
    @Mock
    RequestRepositoryInterface requestRepository;
    @Mock
    AppointmentRequestMapper requestMapper;
    @Mock
    AppointmentUseCases appointmentService;
    @InjectMocks
    RequestService requestService;
    AppointmentRequest requestTest;
    AppointmentsResponse appointmentsResponse;
    RequestModel requestModel;

    @BeforeEach
    void setUp() {
        requestTest = AppointmentRequest.builder()
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

        requestModel = RequestModel.builder()
            .patientIdRequest("PATIENT-ID")
            .requestSpeciality("NEUROLOGY")
            .doctorIdRequest("DOCTOR-ID")
            .requestDate("12/12/2020 12:00")
            .requestDetail("")
            .build();
    }


    @Test
    void testCreateRequestSuccess() {
        when(requestRepository.createRequest(any(AppointmentRequest.class)))
            .thenReturn(Mono.just(requestTest));
        when(appointmentService.createAppointment(any(Appointment.class)))
            .thenReturn(Mono.just(appointmentsResponse));
        when(requestMapper.mapAppointmentRequestToResponse(any()))
            .thenReturn(appointmentsResponse);

        GenericResponse result = requestService.createRequest(requestModel).block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(appointmentsResponse.getRequestId(), result.getData().get(0).getRequestId());
        Assertions.assertEquals(appointmentsResponse.getPatientIdRequest(), result.getData().get(0).getPatientIdRequest());
        Assertions.assertEquals(appointmentsResponse.getDoctorIdRequest(), result.getData().get(0).getDoctorIdRequest());

    }
}
