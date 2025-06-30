package com.example.basic.persons.insfrastructure.repositories;

import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@DataMongoTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        Patient patient = Patient.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.PATIENT)
            .isActive(true)
            .birthDate(LocalDate.now())
            .build();

        patientRepository.deleteAll()
            .then(patientRepository.save(patient))
            .block();
    }

    @Test
    void testFindByPersonID() {
        Mono<Patient> result = patientRepository.findByPersonID("ID");

        StepVerifier.create(result)
            .expectNextMatches(p -> p.getPersonName().equals("NAME"))
            .verifyComplete();
    }

    @Test
    void testFindByPersonNameAndSurname() {
        Flux<Patient> result = patientRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase("NAME", "SURNAME");

        StepVerifier.create(result)
            .expectNextCount(1)
            .verifyComplete();
    }

    @Test
    void testFindByBirthDateBetween() {
        Flux<Patient> result = patientRepository.findByBirthDateBetween(
            LocalDate.now().minusDays(1),
            LocalDate.now().plusDays(10)
        );

        StepVerifier.create(result)
            .expectNextMatches(p -> p.getPersonID().equals("ID"))
            .verifyComplete();
    }

    @Test
    void testExistsByPersonID() {
        Mono<Boolean> result = patientRepository.existsByPersonID("ID");

        StepVerifier.create(result)
            .expectNext(true)
            .verifyComplete();
    }
}
