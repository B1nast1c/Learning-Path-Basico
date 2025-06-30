package com.example.basic.persons.application.ports.ouput.repository;

import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.domain.models.Patient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PatientRepositoryInterface {
    Mono<Patient> findById(String patientId);
    Flux<Patient> findByFullName(String name, String surname);
    Flux<Patient> findByBirthDate(String initDate, String endDate);
    Mono<Patient> createPatient(PatientRequest request);
}
