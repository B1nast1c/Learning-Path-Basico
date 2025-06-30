package com.example.basic.appointments.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class RequestServiceTest {
    
    
    
    com.example.basic.appointments.application.services.RequestService requestService = new com.example.basic.appointments.application.services.RequestService(new com.example.basic.appointments.infrastructure.adapters.output.repositories.RequestsRepository(null, null, null, new com.example.basic.appointments.domain.services.AppointmentRequestValidationService()), new com.example.basic.appointments.application.utils.AppointmentRequestMapper(new org.modelmapper.ModelMapper()), new com.example.basic.appointments.application.services.AppointmentService(new com.example.basic.appointments.infrastructure.adapters.output.repositories.AppointmentsRepository(null, new com.example.basic.appointments.domain.services.AppointmentValidationService()), new com.example.basic.appointments.application.utils.AppointmentMapper(new org.modelmapper.ModelMapper())));

    @Test
    void testCreateRequest(){
        reactor.core.publisher.Mono<com.example.basic.appointments.application.ports.output.models.GenericResponse> result = requestService.createRequest(new com.example.basic.appointments.application.ports.input.models.RequestModel("patientIdRequest", "requestSpeciality", "doctorIdRequest", "requestDate", "requestDetail"));
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme