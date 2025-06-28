package com.example.basic.appointments.infrastructure.repositories;

import com.example.basic.appointments.domain.models.Appointment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface AppointmentRepository extends ReactiveMongoRepository<Appointment, String> {
    Flux<Appointment> findByPatientId(String patientId);

    Flux<Appointment> findByDoctorId(String doctorId);

    Flux<Appointment> findByAppointmentDateBetween(LocalDate appointmentDateStart, LocalDate appointmentDateEnd);

    Flux<Appointment> findByPatientIdAndDoctorId(String patientId, String doctorId);
}
