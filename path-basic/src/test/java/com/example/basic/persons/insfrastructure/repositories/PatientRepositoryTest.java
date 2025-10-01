package com.example.basic.persons.insfrastructure.repositories;

import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDate;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        Patient patientAlice = Patient.builder()
                .personID("PAT-1-ID")
                .personAppID("APP-ID-PAT-1")
                .personName("Alice")
                .personSurname("Smith")
                .birthDate(LocalDate.of(1990, 1, 1))
                .personRole(AppRoles.PATIENT)
                .isActive(true)
                .build();

        Patient patientBob = Patient.builder()
                .personID("PAT-2-ID")
                .personAppID("PAT-ID-DOC-2")
                .personName("Bob")
                .personSurname("Smith")
                .birthDate(LocalDate.of(1985, 5, 10))
                .personRole(AppRoles.PATIENT)
                .isActive(true)
                .build();

        Patient patientCharlie = Patient.builder()
                .personID("PAT-3-ID")
                .personAppID("PAT-ID-DOC-3")
                .personName("Charlie")
                .personSurname("Brown")
                .birthDate(LocalDate.of(2000, 12, 20))
                .personRole(AppRoles.PATIENT)
                .isActive(true)
                .build();

        patientRepository.deleteAll()
                .thenMany(patientRepository.saveAll(Flux.just(patientAlice, patientBob, patientCharlie)))
                .blockLast();
    }

    @Test
    void testFindByPersonID() {
        Mono<Patient> result = patientRepository.findByPersonID("PAT-1-ID");
        StepVerifier.create(result)
                .expectNextMatches(p -> p.getPersonName().equals("Alice"))
                .verifyComplete();
    }

    @Test
    void testFindByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase() {
        Flux<Patient> result = patientRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase("alice", "smith");
        StepVerifier.create(result)
                .expectNextMatches(p -> p.getPersonID().equals("PAT-1-ID"))
                .verifyComplete();
    }

    @Test
    void testFindByBirthDateBetween() {
        LocalDate start = LocalDate.of(1980, 1, 1);
        LocalDate end = LocalDate.of(1995, 12, 31);
        Flux<Patient> result = patientRepository.findByBirthDateBetween(start, end);
        StepVerifier.create(result.collectList())
                .assertNext(list -> {
                    boolean hasAlice = list.stream().anyMatch(p -> p.getPersonName().equals("Alice"));
                    boolean hasBob = list.stream().anyMatch(p -> p.getPersonName().equals("Bob"));
                    assert hasAlice : "Alice not found";
                    assert hasBob : "Bob not found";
                })
                .verifyComplete();
    }

    @Test
    void testExistsByPersonID() {
        Mono<Boolean> exists = patientRepository.existsByPersonID("PAT-1-ID");
        StepVerifier.create(exists)
                .expectNext(true)
                .verifyComplete();

        Mono<Boolean> notExists = patientRepository.existsByPersonID("P99");
        StepVerifier.create(notExists)
                .expectNext(false)
                .verifyComplete();
    }
}