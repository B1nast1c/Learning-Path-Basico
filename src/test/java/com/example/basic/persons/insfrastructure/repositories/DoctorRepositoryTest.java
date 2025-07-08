package com.example.basic.persons.insfrastructure.repositories;

import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        Doctor doctor = Doctor.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.DOCTOR)
            .isActive(true)
            .specialization("NEUROLOGY")
            .build();

        doctorRepository.deleteAll()
            .then(doctorRepository.save(doctor))
            .block();
    }

    @Test
    void testFindBySpecializationIgnoreCaseOrderByPersonNameAsc() {
        Flux<Doctor> result = doctorRepository.findBySpecializationIgnoreCaseOrderByPersonNameAsc("NEUROLOGY");

        StepVerifier.create(result)
            .expectNextMatches(doc -> doc.getPersonName().equals("NAME"))
            .verifyComplete();
    }

    @Test
    void testFindByPersonID() {
        Mono<Doctor> result = doctorRepository.findByPersonID("ID");

        StepVerifier.create(result)
            .expectNextMatches(doc -> doc.getPersonSurname().equals("SURNAME"))
            .verifyComplete();
    }

    @Test
    void testExistsByPersonID() {
        Mono<Boolean> result = doctorRepository.existsByPersonID("ID");

        StepVerifier.create(result)
            .expectNext(true)
            .verifyComplete();
    }

    @Test
    void testFindByPersonNameAndSurname() {
        Flux<Doctor> result = doctorRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase("NAME", "SURNAME");

        StepVerifier.create(result)
            .expectNextCount(1)
            .verifyComplete();
    }
}

