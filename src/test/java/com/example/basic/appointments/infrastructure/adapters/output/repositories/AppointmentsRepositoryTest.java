package com.example.basic.appointments.infrastructure.adapters.output.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class AppointmentsRepositoryTest {
    
    
    com.example.basic.appointments.infrastructure.adapters.output.repositories.AppointmentsRepository appointmentsRepository = new com.example.basic.appointments.infrastructure.adapters.output.repositories.AppointmentsRepository(null, new com.example.basic.appointments.domain.services.AppointmentValidationService());

    @Test
    void testSaveAppointment(){
        reactor.core.publisher.Mono<com.example.basic.appointments.domain.models.Appointment> result = appointmentsRepository.saveAppointment(null);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testUpdateAppointment(){
        reactor.core.publisher.Mono<com.example.basic.appointments.domain.models.Appointment> result = appointmentsRepository.updateAppointment("appointmentId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentById(){
        reactor.core.publisher.Mono<com.example.basic.appointments.domain.models.Appointment> result = appointmentsRepository.getAppointmentById("appointmentId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsByPatient(){
        reactor.core.publisher.Flux<com.example.basic.appointments.domain.models.Appointment> result = appointmentsRepository.getAppointmentsByPatient("patientId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsByDoctor(){
        reactor.core.publisher.Flux<com.example.basic.appointments.domain.models.Appointment> result = appointmentsRepository.getAppointmentsByDoctor("doctorId");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsByDateRange(){
        reactor.core.publisher.Flux<com.example.basic.appointments.domain.models.Appointment> result = appointmentsRepository.getAppointmentsByDateRange("initDate", "endDate");
        Assertions.assertEquals(null, result);
    }

    @Test
    void testGetAppointmentsByPersons(){
        reactor.core.publisher.Flux<com.example.basic.appointments.domain.models.Appointment> result = appointmentsRepository.getAppointmentsByPersons("patientId", "doctorId");
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme