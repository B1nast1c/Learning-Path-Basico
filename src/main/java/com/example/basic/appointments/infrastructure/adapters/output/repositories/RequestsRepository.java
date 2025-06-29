package com.example.basic.appointments.infrastructure.adapters.output.repositories;

import com.example.basic.appointments.application.ports.output.repository.RequestRepositoryInterface;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.appointments.domain.services.AppointmentRequestValidationService;
import com.example.basic.appointments.infrastructure.repositories.RequestRepository;
import com.example.basic.exceptions.throwables.*;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;
import com.example.basic.persons.insfrastructure.repositories.DoctorRepository;
import com.example.basic.persons.insfrastructure.repositories.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RequestsRepository implements RequestRepositoryInterface {
    private final RequestRepository requestRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRequestValidationService validationService;

    public RequestsRepository(
        RequestRepository repository,
        DoctorRepository repositoryD,
        PatientRepository repositoryP,
        AppointmentRequestValidationService service) {
        this.requestRepository = repository;
        this.doctorRepository = repositoryD;
        this.patientRepository = repositoryP;
        this.validationService = service;
    }

    @Override
    public Mono<AppointmentRequest> createRequest(AppointmentRequest request) {
        log.info("request.adapters.output.repository::createRequest()");

        return requestRepository.existsByRequestId(request.getRequestId())
            .flatMap(exists -> {
                if (exists.equals(Boolean.TRUE)) return Mono.error(new DuplicateExc("Request already registered"));

                Mono<Doctor> doctorMono = doctorRepository.findById(request.getDoctorIdRequest());
                Mono<Patient> patientMono = patientRepository.findByPersonID(request.getPatientIdRequest());
                Flux<AppointmentRequest> existingAppointments =
                    requestRepository.findByDoctorIdRequestAndRequestDate(request.getDoctorIdRequest(), request.getRequestDate());

                return validationService
                    .validate(request, doctorMono, patientMono, existingAppointments)
                    .flatMap(requestRepository::save)
                    .doOnSuccess(saved -> log.info("Appointment Request saved successfully"))
                    .doOnError(error -> log.error(error.getMessage()));
            });
    }

}
