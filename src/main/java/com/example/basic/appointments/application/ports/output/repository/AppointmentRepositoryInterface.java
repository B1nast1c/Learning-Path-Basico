package com.example.basic.appointments.application.ports.output.repository;

import com.example.basic.appointments.domain.models.Appointment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AppointmentRepositoryInterface {
    Mono<Appointment> saveAppointment(Appointment appointment);
    Mono<Appointment> updateAppointment(String appointmentId);
    Mono<Appointment> getAppointmentById(String appointmentId);
    Flux<Appointment> getAppointmentsByPatient(String patientId);
    Flux<Appointment> getAppointmentsByDoctor(String doctorId);
    Flux<Appointment> getAppointmentsByDateRange(String initDate, String endDate);
    Flux<Appointment> getAppointmentsByPersons(String patientId, String doctorId);
}
