package com.example.basic.appointments.infrastructure.adapters.output.repositories;

import com.example.basic.appointments.application.ports.output.repository.RequestRepositoryInterface;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.appointments.domain.validations.AppointmentValidations;
import com.example.basic.appointments.domain.validations.DateValidations;
import com.example.basic.appointments.infrastructure.repositories.RequestRepository;
import com.example.basic.exceptions.throwables.*;
import com.example.basic.persons.insfrastructure.repositories.DoctorRepository;
import com.example.basic.persons.insfrastructure.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RequestsRepository implements RequestRepositoryInterface {
    private final RequestRepository requestRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public RequestsRepository(
        RequestRepository repository,
        DoctorRepository repositoryD,
        PatientRepository repositoryP) {
        this.requestRepository = repository;
        this.doctorRepository = repositoryD;
        this.patientRepository = repositoryP;
    }

    @Override
    public Mono<AppointmentRequest> createRequest(AppointmentRequest request) {
        log.info("request.adapters.output.repository::createRequest()");

        return requestRepository.existsByRequestId(request.getRequestId())
            .flatMap(exists -> {
                if (Boolean.TRUE.equals(exists)) { return Mono.error(new DuplicateExc("Request already registered")); }
                return Mono.just(request)
                    .filter(req -> AppointmentValidations.validatePersons(req.getDoctorIdRequest(), req.getPatientIdRequest()))
                    .switchIfEmpty(Mono.error(new GenericExc("Doctor and patient must be different")))
                    .flatMap(validRequest ->
                        patientRepository.findByPersonID(validRequest.getPatientIdRequest())
                            .switchIfEmpty(Mono.error(new NotFoundExc("No patient was found")))
                            .flatMap(patient ->
                                doctorRepository.findById(validRequest.getDoctorIdRequest())
                                    .switchIfEmpty(Mono.error(new NotFoundExc("No doctor was found")))
                                    .filter(doctor -> AppointmentValidations.validateSpeciality(doctor, validRequest.getRequestSpeciality().name()))
                                    .switchIfEmpty(Mono.error(new SpecialityExc("The doctor's speciality does not match")))
                                    .filter(doctor -> DateValidations.isYearValid(validRequest.getRequestDate().getYear()))
                                    .switchIfEmpty(Mono.error(new DateFormatExc("The appointment year must be between 1900 and 2100")))
                                    .filter(doctor -> DateValidations.validateHour(validRequest.getRequestDate()))
                                    .switchIfEmpty(Mono.error(new DateFormatExc("The appointment request must be during working time")))
                                    .flatMapMany(validatedDoctor -> {
                                        validRequest.setPatientFullName(patient.getPersonName() + " " + patient.getPersonSurname());
                                        validRequest.setDoctorFullName(validatedDoctor.getPersonName() + " " + validatedDoctor.getPersonSurname());
                                        return requestRepository.findByDoctorIdRequestAndRequestDate(request.getDoctorIdRequest(), request.getRequestDate())
                                            .filter(appointments -> !DateValidations.validateDates(request.getRequestDate(), appointments.getRequestDate()));
                                        }
                                    )
                                    .hasElements()
                                    .flatMap(hasConflict -> {
                                        if (Boolean.TRUE.equals(hasConflict)) {
                                            return Mono.error(new DuplicateExc("The doctor has its schedule taken"));
                                        }
                                        return Mono.just(validRequest);
                                    })
                            )
                    ).flatMap(requestRepository::save)
                    .doOnNext(doctors -> log.info("Appointment Request saved successfully"))
                    .doOnError(error -> log.error(error.getMessage()));
            });
    }
}
