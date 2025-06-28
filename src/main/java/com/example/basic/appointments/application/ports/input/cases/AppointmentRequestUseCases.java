package com.example.basic.appointments.application.ports.input.cases;

import com.example.basic.appointments.application.ports.input.models.RequestModel;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import reactor.core.publisher.Mono;

public interface AppointmentRequestUseCases {
    Mono<GenericResponse> createRequest(RequestModel request);
}
