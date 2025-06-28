package com.example.basic.persons.insfrastructure.adapters.output.repository;

import com.example.basic.exceptions.throwables.DateFormatExc;
import com.example.basic.exceptions.throwables.DuplicateExc;
import com.example.basic.exceptions.throwables.NotFoundExc;
import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.application.ports.ouput.repository.PatientRepositoryInterface;
import com.example.basic.persons.application.utils.DateParser;
import com.example.basic.persons.domain.models.Patient;
import com.example.basic.persons.domain.models.Person;
import com.example.basic.persons.domain.validations.DateValidations;
import com.example.basic.persons.domain.validations.FieldsValidations;
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

    public PatientsRepository(PatientRepository repository) {
        this.patientRepository = repository;
    }

    @Override
    public Mono<Patient> findById(String patientId) {
        log.info("patient.adapters.output.repository::findById()");
        return patientRepository.findByPersonID(patientId)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundExc("Patient not found"))));
    }

    @Override
    public Flux<Patient> findByFullName(String name, String surname) {
        log.info("patient.adapters.output.repository::findByFullName()");
        return patientRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(name, surname)
            .filter(Person::isActive)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundExc("Not enough data to retrieve"))));
    }

    @Override
    public Flux<Patient> findByBirthDate(String initDate, String endDate) {
        log.info("patient.adapters.output.repository::findByBirthDate()");
        try {
            // El rango de fechas NO contempla a la actual, contempla al dia antes y despues de los límites
            // Por eso se disminuye un dia en la fecha de inicio, y se aumenta un día en la fecha de fin
            LocalDate mappedInit = DateParser.parseDate(initDate).minusDays(1);
            LocalDate mappedEnd = DateParser.parseDate(endDate).plusDays(1);
            boolean isValid = DateValidations.validateDateRanges(mappedInit, mappedEnd);
            if (!isValid) {
                return Flux.error(new DateFormatExc("Invalid date range"));
            }
            return patientRepository.findByBirthDateBetween(mappedInit, mappedEnd)
                .switchIfEmpty(Flux.error(new NotFoundExc("There are no patients available")));
        } catch (Exception e) {
            return Flux.error(new DateFormatExc("Invalid date format: " + e.getMessage()));
        }
    }

    @Override
    public synchronized Mono<Patient> createPatient(Patient patient) {
        log.info("patient.adapters.output.repository::createPatient()");
        return patientRepository.existsByPersonID(patient.getPersonID())
            .filter(exists -> !exists)
            .flatMap(found -> {
                boolean isValidId = FieldsValidations.validateInput(patient.getPersonID());
                boolean isValidDate = DateValidations.validateBirthDate(patient.getBirthDate());
                if (isValidId && isValidDate) {
                    return patientRepository.save(patient)
                        .doOnNext(doctors -> log.info("Entity saved successfully"))
                        .doOnError(error -> log.error(error.getMessage()));
                }
                return Mono.defer(() -> Mono.error(new SpecialityExc("Invalid request format")));
            })
            .switchIfEmpty(Mono.defer(() -> Mono.error(new DuplicateExc("Entity already in the database"))));
    }

    @Override
    public Mono<Patient> deletePatient(String patientId) {
        log.info("patient.adapters.output.repository::deletePatient()");
        return patientRepository.findByPersonID(patientId)
            .filter(Person::isActive)
            .flatMap(found -> {
                found.setActive(Boolean.FALSE);
                return patientRepository.save(found)
                    .doOnNext(doctors -> log.info("Entity deleted successfully"))
                    .doOnError(error -> log.error(error.getMessage()));
            })
            .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundExc("Cannot find an entity to modify"))));
    }
}
