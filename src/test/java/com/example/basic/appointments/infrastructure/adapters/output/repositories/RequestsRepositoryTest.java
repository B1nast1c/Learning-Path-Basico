package com.example.basic.appointments.infrastructure.adapters.output.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class RequestsRepositoryTest {
    
    
    
    
    com.example.basic.appointments.infrastructure.adapters.output.repositories.RequestsRepository requestsRepository = new com.example.basic.appointments.infrastructure.adapters.output.repositories.RequestsRepository(null, null, null, new com.example.basic.appointments.domain.services.AppointmentRequestValidationService());

    @Test
    void testCreateRequest(){
        reactor.core.publisher.Mono<com.example.basic.appointments.domain.models.AppointmentRequest> result = requestsRepository.createRequest(null);
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme