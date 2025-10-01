package com.example.basic.persons.insfrastructure.repositories;

import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        Doctor doctorAlice = Doctor.builder()
                .personID("DOC-1-ID")
                .personAppID("APP-ID-DOC-1")
                .personName("Alice")
                .personSurname("Smith")
                .personRole(AppRoles.DOCTOR)
                .isActive(true)
                .specialization("Cardiology")
                .build();

        Doctor doctorBob = Doctor.builder()
                .personID("DOC-2-ID")
                .personAppID("APP-ID-DOC-2")
                .personName("Bob")
                .personSurname("Smith")
                .personRole(AppRoles.DOCTOR)
                .isActive(true)
                .specialization("cardiology")
                .build();

        Doctor doctorCharlie = Doctor.builder()
                .personID("DOC-3-ID")
                .personAppID("APP-ID-DOC-3")
                .personName("Charlie")
                .personSurname("Brown")
                .personRole(AppRoles.DOCTOR)
                .isActive(true)
                .specialization("Neurology")
                .build();

        doctorRepository.deleteAll()
                .thenMany(doctorRepository
                        .saveAll(Flux.just(doctorAlice, doctorBob, doctorCharlie)))
                .blockLast();
    }

    @Test
    void testFindBySpecializationIgnoreCaseOrderByPersonNameAsc() {
        Flux<Doctor> result = doctorRepository
                .findBySpecializationIgnoreCaseOrderByPersonNameAsc("cardiology");
        StepVerifier.create(result)
                .expectNextMatches(d -> d.getPersonName().equals("Alice"))
                .expectNextMatches(d -> d.getPersonName().equals("Bob"))
                .verifyComplete();
    }

    @Test
    void testFindByPersonID() {
        Mono<Doctor> result = doctorRepository.findByPersonID("DOC-1-ID");
        StepVerifier.create(result)
                .expectNextMatches(d -> d.getPersonName().equals("Alice"))
                .verifyComplete();
    }

    @Test
    void testExistsByPersonID() {
        Mono<Boolean> exists = doctorRepository.existsByPersonID("DOC-2-ID");
        StepVerifier.create(exists)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> notExists = doctorRepository.existsByPersonID("P99");
        StepVerifier.create(notExists)
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void testFindByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase() {
        Flux<Doctor> result = doctorRepository
                .findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase("alice", "smith");
        StepVerifier.create(result)
                .expectNextMatches(d -> d.getPersonID().equals("DOC-1-ID"))
                .verifyComplete();
    }
}