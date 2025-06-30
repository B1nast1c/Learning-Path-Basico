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

/**
 * Implementación del repositorio de solicitudes de citas médicas.
 * Esta clase se encarga de validar y guardar solicitudes de citas en la base de datos,
 * asegurándose de que no haya duplicados y que los datos del doctor y paciente sean válidos.
 */
@Slf4j
@Component
public class RequestsRepository implements RequestRepositoryInterface {

    private final RequestRepository requestRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRequestValidationService validationService;

    /**
     * Constructor que recibe los repositorios necesarios y el servicio de validación.
     */
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

    /**
     * Crea una nueva solicitud de cita médica.
     * Primero verifica que no exista una solicitud con el mismo ID.
     * Luego valida los datos del doctor, paciente y la fecha.
     * Si todo es válido, guarda la solicitud en la base de datos.
     *
     * @param request La solicitud de cita a guardar.
     * @return La solicitud guardada o un error si algo falla.
     */
    @Override
    public Mono<AppointmentRequest> createRequest(AppointmentRequest request) {
        log.info("request.adapters.output.repository::createRequest()");

        return requestRepository.existsByRequestId(request.getRequestId())
            .flatMap(exists -> {
                if (exists.equals(Boolean.TRUE)) {
                    return Mono.error(new DuplicateExc("Request already registered"));
                }

                Mono<Doctor> doctorMono = doctorRepository.findById(request.getDoctorIdRequest());
                Mono<Patient> patientMono = patientRepository.findByPersonID(request.getPatientIdRequest());
                Flux<AppointmentRequest> existingAppointments =
                    requestRepository.findByDoctorIdRequestAndRequestDate(
                        request.getDoctorIdRequest(), request.getRequestDate());

                return validationService
                    .validate(request, doctorMono, patientMono, existingAppointments)
                    .flatMap(requestRepository::save)
                    .doOnSuccess(saved -> log.info("Appointment Request saved successfully"))
                    .doOnError(error -> log.error(error.getMessage()));
            });
    }
}