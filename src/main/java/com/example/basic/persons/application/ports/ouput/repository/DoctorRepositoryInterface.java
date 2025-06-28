package com.example.basic.persons.application.ports.ouput.repository;

import com.example.basic.persons.domain.models.Doctor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DoctorRepositoryInterface {
    Flux<Doctor> findAll();
    Flux<Doctor> findBySpeciality(String speciality);
    Mono<Doctor> findById(String doctorId);
    Flux<Doctor> findByFullName(String name, String surname);
    Mono<Doctor> createDoctor(Doctor doctor);
    Mono<Doctor> deleteDoctor(String doctorId);
}
