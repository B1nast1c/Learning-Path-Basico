package com.example.basic.persons.insfrastructure.repositories;

import com.example.basic.persons.domain.models.Patient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PatientRepository extends ReactiveMongoRepository<Patient, UUID> {
    Mono<Patient> findByPersonID(String personID);

    Flux<Patient> findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(String personName, String personSurname);

    Flux<Patient> findByBirthDateBetween(LocalDate birthDateStart, LocalDate birthDateEnd);

    Mono<Boolean> existsByPersonID(String personID);
}
