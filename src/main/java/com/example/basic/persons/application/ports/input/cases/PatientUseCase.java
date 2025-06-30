package com.example.basic.persons.application.ports.input.cases;

import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import reactor.core.publisher.Mono;

public interface PatientUseCase {
    Mono<GenericResponse> getPatient(String patientId);
    Mono<GenericResponse> getPatientsByName(String name, String surname);
    Mono<GenericResponse> getPatientsByDate(String initDate, String endDate);
    Mono<GenericResponse> createPatient(PatientRequest patient);
}
