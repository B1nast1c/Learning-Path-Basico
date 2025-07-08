package com.example.basic.appointments.infrastructure.repositories;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@DataMongoTest
class RequestRepositoryTest {
    @Autowired
    RequestRepository requestRepository;

    @BeforeEach
    void setUp() {
        AppointmentRequest appointmentRequest = AppointmentRequest.builder()
            .requestId("REQ-TEST")
            .patientIdRequest("PATIENT-ID")
            .patientFullName("PATIENT")
            .doctorIdRequest("DOCTOR-ID")
            .doctorFullName("DOCTOR")
            .requestDate(LocalDateTime.now())
            .isValid(true)
            .requestDetail("")
            .requestSpeciality(Specializations.OBSTETRICS)
            .build();

        requestRepository.deleteAll()
            .thenMany(requestRepository.saveAll(Flux.just(appointmentRequest)))
            .blockLast();
    }

    @Test
    void testExistsByRequestId() {
        Mono<Boolean> exists = requestRepository.existsByRequestId("REQ-TEST");

        StepVerifier.create(exists)
            .expectNext(true)
            .verifyComplete();
    }

    @Test
    void testFindByDoctorIdRequestAndRequestDate() {
        StepVerifier.create(requestRepository.findByDoctorIdRequestAndRequestDate("DOCTOR-ID", LocalDateTime.of(2025, 6, 29, 10, 0)))
            .verifyComplete();
    }
}

