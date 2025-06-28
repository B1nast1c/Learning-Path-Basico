package com.example.basic.persons.application.ports.input.cases;

import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import reactor.core.publisher.Mono;

public interface DoctorUseCase {
    Mono<GenericResponse> getDoctors();
    Mono<GenericResponse> getDoctorsBySpeciality(String speciality);
    Mono<GenericResponse> getDoctor(String doctorID);
    Mono<GenericResponse> getDoctorByFullName(String name, String surname);
    Mono<GenericResponse> createDoctor(DoctorRequest request);
    Mono<GenericResponse> removeDoctor(String doctorID);
}
