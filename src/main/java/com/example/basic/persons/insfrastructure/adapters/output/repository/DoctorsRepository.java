package com.example.basic.persons.insfrastructure.adapters.output.repository;

import com.example.basic.exceptions.throwables.DuplicateExc;
import com.example.basic.exceptions.throwables.NotFoundExc;
import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.application.ports.ouput.repository.DoctorRepositoryInterface;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Person;
import com.example.basic.persons.domain.services.DoctorValidationService;
import com.example.basic.persons.insfrastructure.repositories.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DoctorsRepository implements DoctorRepositoryInterface {

    private final DoctorRepository doctorRepository;
    private final DoctorValidationService doctorValidationService;

    public DoctorsRepository(
        DoctorRepository repository,
        DoctorValidationService validationService) {
        this.doctorRepository = repository;
        this.doctorValidationService = validationService;
    }

    @Override
    public Flux<Doctor> findAll() {
        log.info("doctor.adapters.output.repository::findAll()");
        return doctorRepository.findAll();
    }

    @Override
    public Flux<Doctor> findBySpeciality(String speciality) {
        log.info("doctor.adapters.output.repository::findBySpeciality()");
        return Mono.fromCallable(() -> doctorValidationService.validateSpeciality(speciality))
            .flatMapMany(isValid -> {
                if (isValid.equals(Boolean.TRUE)) {
                    return doctorRepository.findBySpecializationIgnoreCaseOrderByPersonNameAsc(speciality);
                }
                return Flux.error(new SpecialityExc("Invalid speciality"));
            })
            .switchIfEmpty(Mono.error(new NotFoundExc("There are no doctors available")));
    }

    @Override
    public Mono<Doctor> findById(String doctorId) {
        log.info("doctor.adapters.output.repository::findById()");
        return doctorRepository.findByPersonID(doctorId)
            .switchIfEmpty(Mono.error(new NotFoundExc("Doctor not found")));
    }

    @Override
    public Flux<Doctor> findByFullName(String name, String surname) {
        log.info("doctor.adapters.output.repository::findByFullName()");
        return doctorRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(name, surname)
            .filter(Person::isActive)
            .switchIfEmpty(Mono.error(new NotFoundExc("Not enough data to retrieve")));
    }

    @Override
    public Mono<Doctor> createDoctor(Doctor doctor) {
        log.info("doctor.adapters.output.repository::createDoctor()");
        return doctorRepository.existsByPersonID(doctor.getPersonID())
            .filter(exists -> !exists)
            .flatMap(found -> {
                try {
                    doctorValidationService.validate(doctor);
                    return doctorRepository.save(doctor)
                        .doOnSuccess(saved -> log.info("Entity saved successfully"));
                } catch (Exception e) {
                    return Mono.error(e);
                }
            })
            .switchIfEmpty(Mono.error(new DuplicateExc("Entity already in the database")));
    }

    @Override
    public Mono<Doctor> deleteDoctor(String doctorId) {
        log.info("doctor.adapters.output.repository::deleteDoctor()");
        return doctorRepository.findByPersonID(doctorId)
            .filter(Person::isActive)
            .flatMap(found -> {
                found.setActive(Boolean.FALSE);
                return doctorRepository.save(found)
                    .doOnSuccess(deleted -> log.info("Entity deleted successfully"));
            })
            .switchIfEmpty(Mono.error(new NotFoundExc("Cannot find an entity to modify")));
    }
}
