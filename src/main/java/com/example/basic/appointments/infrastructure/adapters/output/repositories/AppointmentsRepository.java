package com.example.basic.appointments.infrastructure.adapters.output.repositories;

import com.example.basic.appointments.application.ports.output.repository.AppointmentRepositoryInterface;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.services.AppointmentValidationService;
import com.example.basic.appointments.infrastructure.repositories.AppointmentRepository;
import com.example.basic.exceptions.throwables.DateFormatExc;
import com.example.basic.exceptions.throwables.NotFoundExc;
import com.example.basic.persons.application.utils.builders.DateParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Component
public class AppointmentsRepository implements AppointmentRepositoryInterface {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentValidationService validationService;

    public AppointmentsRepository(AppointmentRepository repository, AppointmentValidationService validationService) {
        this.appointmentRepository = repository;
        this.validationService = validationService;
    }

    @Override
    public Mono<Appointment> saveAppointment(Appointment appointment) {
        log.info("request.adapters.output.repository::saveAppointment()");

        try {
            validationService.validateAppointmentDate(appointment);
            return appointmentRepository.save(appointment)
                .doOnSuccess(saved -> log.info("Appointment saved successfully: " + saved.getAppointmentId()));
        } catch (Exception e) {
            log.error("Validation failed: " + e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Appointment> updateAppointment(String appointmentId) {
        log.info("request.adapters.output.repository::updateAppointment");

        return Mono.error(new UnsupportedOperationException("Update not implemented yet"));
    }

    @Override
    public Mono<Appointment> getAppointmentById(String appointmentId) {
        log.info("request.adapters.output.repository::getAppointmentById()");

        return appointmentRepository.findById(appointmentId)
            .switchIfEmpty(Mono.error(new NotFoundExc("Appointment not found")));
    }

    @Override
    public Flux<Appointment> getAppointmentsByPatient(String patientId) {
        log.info("request.adapters.output.repository::getAppointmentsByPatient()");

        return appointmentRepository.findByPatientId(patientId)
            .switchIfEmpty(Mono.error(new NotFoundExc("The patient has no appointments")));
    }

    @Override
    public Flux<Appointment> getAppointmentsByDoctor(String doctorId) {
        log.info("request.adapters.output.repository::getAppointmentsByDoctor()");

        return appointmentRepository.findByDoctorId(doctorId)
            .switchIfEmpty(Mono.error(new NotFoundExc("No appointments have been registered")));
    }

    @Override
    public Flux<Appointment> getAppointmentsByDateRange(String initDate, String endDate) {
        log.info("request.adapters.output.repository::getAppointmentsByDateRange()");

        try {
            LocalDate mappedInit = DateParser.parseDate(initDate).minusDays(1);
            LocalDate mappedEnd = DateParser.parseDate(endDate).plusDays(1);
            validationService.validateDateRange(mappedInit, mappedEnd);

            return appointmentRepository.findByAppointmentDateBetween(mappedInit, mappedEnd)
                .switchIfEmpty(Flux.error(new NotFoundExc("There are no appointments available")));
        } catch (Exception e) {
            log.error("Date range validation failed: " + e.getMessage());
            return Flux.error(new DateFormatExc("Invalid date format: " + e.getMessage()));
        }
    }

    @Override
    public Flux<Appointment> getAppointmentsByPersons(String patientId, String doctorId) {
        log.info("request.adapters.output.repository::getAppointmentsByPersons()");

        return appointmentRepository.findByPatientIdAndDoctorId(patientId, doctorId)
            .switchIfEmpty(Mono.error(new NotFoundExc("The appointment made by the patient" + patientId + "with the doctor" + doctorId + "does not exist")));
    }
}
