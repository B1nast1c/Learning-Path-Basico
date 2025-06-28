package com.example.basic.appointments.infrastructure.adapters.output.repositories;

import com.example.basic.appointments.application.ports.output.repository.AppointmentRepositoryInterface;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.infrastructure.repositories.AppointmentRepository;
import com.example.basic.exceptions.throwables.DateFormatExc;
import com.example.basic.exceptions.throwables.NotFoundExc;
import com.example.basic.persons.application.utils.DateParser;
import com.example.basic.persons.domain.validations.DateValidations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Component
public class AppointmentsRepository implements AppointmentRepositoryInterface {
    private final AppointmentRepository appointmentRepository;

    public AppointmentsRepository(AppointmentRepository repository) {
        this.appointmentRepository = repository;
    }

    @Override
    public Mono<Appointment> saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment)
            .doOnNext(doctors -> log.info("Appointment saved successfully"))
            .doOnError(error -> log.error(error.getMessage()));
    }

    @Override
    public Mono<Appointment> updateAppointment(String appointmentId) {
        return null;
    }

    @Override
    public Mono<Appointment> getAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public Flux<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public Flux<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public Flux<Appointment> getAppointmentsByDateRange(String initDate, String endDate) {
        try {
            // El rango de fechas NO contempla a la actual, contempla al dia antes y despues de los límites
            // Por eso se disminuye un dia en la fecha de inicio, y se aumenta un día en la fecha de fin
            LocalDate mappedInit = DateParser.parseDate(initDate).minusDays(1);
            LocalDate mappedEnd = DateParser.parseDate(endDate).plusDays(1);
            boolean isValid = DateValidations.validateDateRanges(mappedInit, mappedEnd);
            if (!isValid) {
                return Flux.error(new DateFormatExc("Invalid date range"));
            }
            return appointmentRepository.findByAppointmentDateBetween(mappedInit, mappedEnd)
                .switchIfEmpty(Flux.error(new NotFoundExc("There are no appointments available")));
        } catch (Exception e) {
            return Flux.error(new DateFormatExc("Invalid date format: " + e.getMessage()));
        }
    }

    @Override
    public Flux<Appointment> getAppointmentsByPersons(String patientId, String doctorId) {
        return appointmentRepository.findByPatientIdAndDoctorId(patientId, doctorId);
    }
}
