package com.example.basic.persons.insfrastructure.repositories;

import com.example.basic.persons.domain.models.Doctor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DoctorRepository extends ReactiveMongoRepository<Doctor, String> {
    Flux<Doctor> findBySpecializationIgnoreCaseOrderByPersonNameAsc(String specialization);

    Mono<Doctor> findByPersonID(String personID);

    Mono<Boolean> existsByPersonID(String personID);

    Flux<Doctor> findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(String personName, String personSurname);
}
