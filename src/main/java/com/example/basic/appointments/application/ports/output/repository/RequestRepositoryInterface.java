package com.example.basic.appointments.application.ports.output.repository;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import reactor.core.publisher.Mono;

public interface RequestRepositoryInterface {
    Mono<AppointmentRequest> createRequest(AppointmentRequest request);
}
