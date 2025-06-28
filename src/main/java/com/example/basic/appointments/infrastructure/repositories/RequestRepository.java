package com.example.basic.appointments.infrastructure.repositories;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface RequestRepository extends ReactiveMongoRepository<AppointmentRequest, String> {
    Mono<Boolean> existsByRequestId(String requestId);

    Flux<AppointmentRequest> findByDoctorIdRequestAndRequestDate(String doctorIdRequest, LocalDateTime requestDate);
}
