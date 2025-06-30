package com.example.basic.persons.insfrastructure.adapters.output.repository;

import com.example.basic.exceptions.throwables.DateFormatExc;
import com.example.basic.exceptions.throwables.DuplicateExc;
import com.example.basic.exceptions.throwables.NotFoundExc;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.repository.PatientRepositoryInterface;
import com.example.basic.persons.application.utils.builders.DateParser;
import com.example.basic.persons.application.utils.builders.RequestBuilder;
import com.example.basic.persons.domain.models.Patient;
import com.example.basic.persons.domain.models.Person;
import com.example.basic.persons.domain.services.PatientValidationService;
import com.example.basic.persons.insfrastructure.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Component
public class PatientsRepository implements PatientRepositoryInterface {

    private final PatientRepository patientRepository;
    private final PatientValidationService patientValidationService;

    public PatientsRepository (
        PatientRepository repository,
        PatientValidationService validationService) {
        this.patientRepository = repository;
        this.patientValidationService = validationService;
    }

    @Override
    public Mono<Patient> findById(String patientId) {
        log.info("patient.adapters.output.repository::findById()");
        return patientRepository.findByPersonID(patientId)
            .switchIfEmpty(Mono.error(new NotFoundExc("Patient not found")));
    }

    @Override
    public Flux<Patient> findByFullName(String name, String surname) {
        log.info("patient.adapters.output.repository::findByFullName()");
        return patientRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(name, surname)
            .filter(Person::isActive)
            .switchIfEmpty(Mono.error(new NotFoundExc("Not enough data to retrieve")));
    }

    @Override
    public Flux<Patient> findByBirthDate(String initDate, String endDate) {
        log.info("patient.adapters.output.repository::findByBirthDate()");
        try {
            LocalDate mappedInit = DateParser.parseDate(initDate).minusDays(1);
            LocalDate mappedEnd = DateParser.parseDate(endDate).plusDays(1);
            if (!patientValidationService.validateDateRange(mappedInit, mappedEnd)) {
                return Flux.error(new DateFormatExc("Invalid date range"));
            }
            return patientRepository.findByBirthDateBetween(mappedInit, mappedEnd)
                .switchIfEmpty(Flux.error(new NotFoundExc("There are no patients available")));
        } catch (Exception e) {
            return Flux.error(new DateFormatExc("Invalid date format: " + e.getMessage()));
        }
    }

    @Override
    public Mono<Patient> createPatient(PatientRequest patient) {
        log.info("patient.adapters.output.repository::createPatient()");
        return patientRepository.existsByPersonID(patient.getPersonID())
            .filter(exists -> !exists)
            .flatMap(found -> {
                try {
                    Patient mapPatient = RequestBuilder.buildPatientRequest(patient);
                    patientValidationService.validate(mapPatient);
                    return patientRepository.save(mapPatient)
                        .doOnSuccess(saved -> log.info("Entity saved successfully"));
                } catch (Exception e) {
                    return Mono.error(e);
                }
            })
            .switchIfEmpty(Mono.error(new DuplicateExc("Entity already in the database")));
    }
}
