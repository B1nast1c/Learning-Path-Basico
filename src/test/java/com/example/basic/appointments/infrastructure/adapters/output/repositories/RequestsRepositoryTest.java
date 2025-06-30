package com.example.basic.appointments.infrastructure.adapters.output.repositories;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.appointments.domain.services.AppointmentRequestValidationService;
import com.example.basic.appointments.infrastructure.repositories.RequestRepository;
import com.example.basic.exceptions.throwables.DuplicateExc;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;
import com.example.basic.persons.domain.models.Specializations;
import com.example.basic.persons.insfrastructure.repositories.DoctorRepository;
import com.example.basic.persons.insfrastructure.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RequestsRepositoryTest {
    @Mock
    RequestRepository testRequestRepository;
    @Mock
    DoctorRepository testDoctorRepository;
    @Mock
    PatientRepository testPatientRepository;
    @Mock
    AppointmentRequestValidationService testValidationService;
    @InjectMocks
    RequestsRepository testRepository;
    AppointmentRequest request;

    @BeforeEach
    void setUp() {
        request = AppointmentRequest.builder()
            .requestId("REQ-TEST")
            .patientIdRequest("PATIENT-ID")
            .patientFullName("PATIENT")
            .doctorIdRequest("DOCTOR-ID")
            .doctorFullName("DOCTOR")
            .requestDate(LocalDateTime.now())
            .requestSpeciality(Specializations.OBSTETRICS)
            .isValid(true)
            .requestDetail("")
            .build();
    }

    private void invokeDuplicateRecord() {
        testRepository.createRequest(request).block();
    }

    @Test
    void createRequestDuplicate() {
        Mockito.when(testRequestRepository.existsByRequestId("REQ-TEST"))
            .thenReturn(Mono.just(true));

        DuplicateExc result = assertThrows(DuplicateExc.class, this::invokeDuplicateRecord);
        assertEquals("Request already registered", result.getMessage());
    }

    @Test
    void createRequest() {
        Doctor doctor = Doctor.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.DOCTOR)
            .isActive(true)
            .specialization("NEUROLOGY")
            .build();
        Patient patient = Patient.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.PATIENT)
            .isActive(true)
            .birthDate(LocalDate.now())
            .build();

        Mockito.when(testRequestRepository.existsByRequestId(Mockito.anyString()))
            .thenReturn(Mono.just(false));
        Mockito.when(testDoctorRepository.findById(Mockito.anyString()))
            .thenReturn(Mono.just(doctor));
        Mockito.when(testPatientRepository.findByPersonID(Mockito.anyString()))
            .thenReturn(Mono.just(patient));
        Mockito.when(testRequestRepository.findByDoctorIdRequestAndRequestDate(Mockito.any(), Mockito.any()))
            .thenReturn(Flux.empty());
        Mockito.when(testValidationService.validate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
            .thenReturn(Mono.just(request));
        Mockito.when(testRequestRepository.save(request)).thenReturn(Mono.just(request));

        StepVerifier.create(testRepository.createRequest(request))
            .expectNextMatches(saved -> saved.getRequestId().equals("REQ-TEST"))
            .verifyComplete();

        Mockito.verify(testRequestRepository).save(request);
    }
}
