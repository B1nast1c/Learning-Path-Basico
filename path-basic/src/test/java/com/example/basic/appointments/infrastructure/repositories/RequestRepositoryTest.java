package com.example.basic.appointments.infrastructure.repositories;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@DataMongoTest
@ExtendWith(MockitoExtension.class)
class RequestRepositoryTest {

    @Mock
    private RequestRepository requestRepository;

    private AppointmentRequest requestTest1;

    @BeforeEach
    void setUp() {
        requestTest1 = AppointmentRequest.builder()
                .requestId("REQ-TEST-1")
                .doctorIdRequest("DOC-ID-1")
                .patientIdRequest("PATIENT-ID-1")
                .requestDate(LocalDateTime.of(2024, 6, 1, 10, 0))
                .build();
    }

    @Test
    void existsByRequestIdReturnsTrue() {
        Mockito.when(requestRepository.existsByRequestId("REQ-TEST-1"))
                .thenReturn(Mono.just(true));

        StepVerifier.create(requestRepository.existsByRequestId("REQ-TEST-1"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void existsByRequestIdReturnsFalse() {
        Mockito.when(requestRepository.existsByRequestId("REQ-404"))
                .thenReturn(Mono.just(false));

        StepVerifier.create(requestRepository.existsByRequestId("REQ-404"))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void findByDoctorIdRequestAndRequestDateReturnsMatchingRequests() {
        Mockito.when(requestRepository.findByDoctorIdRequestAndRequestDate(eq("DOC-ID-1"), any(LocalDateTime.class)))
                .thenReturn(Flux.just(requestTest1));

        StepVerifier
                .create(requestRepository.findByDoctorIdRequestAndRequestDate("DOC-ID-1",
                        LocalDateTime.of(2024, 6, 1, 10, 0)))
                .expectNextMatches(r -> r.getRequestId().equals("REQ-TEST-1") && r.getDoctorIdRequest().equals("DOC-ID-1"))
                .verifyComplete();
    }

    @Test
    void findByDoctorIdRequestAndRequestDateReturnsEmpty() {
        Mockito.when(requestRepository.findByDoctorIdRequestAndRequestDate(eq("DOC-3"), any(LocalDateTime.class)))
                .thenReturn(Flux.empty());

        StepVerifier
                .create(requestRepository.findByDoctorIdRequestAndRequestDate("DOC-3",
                        LocalDateTime.of(2024, 6, 3, 12, 0)))
                .verifyComplete();
    }
}