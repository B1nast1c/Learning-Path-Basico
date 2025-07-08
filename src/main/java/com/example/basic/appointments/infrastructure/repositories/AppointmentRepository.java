package com.example.basic.appointments.infrastructure.repositories;

import com.example.basic.appointments.domain.models.Appointment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

/**
 * Repositorio para acceder a los datos de citas médicas en MongoDB.
 * Extiende de ReactiveMongoRepository para trabajar de forma reactiva con la base de datos.
 */
@Repository
public interface AppointmentRepository extends ReactiveMongoRepository<Appointment, String> {

    /**
     * Busca todas las citas asociadas a un paciente.
     *
     * @param patientId ID del paciente.
     * @return Lista reactiva de citas del paciente.
     */
    Flux<Appointment> findByPatientId(String patientId);

    /**
     * Busca todas las citas asociadas a un doctor.
     *
     * @param doctorId ID del doctor.
     * @return Lista reactiva de citas del doctor.
     */
    Flux<Appointment> findByDoctorId(String doctorId);

    /**
     * Busca todas las citas dentro de un rango de fechas.
     *
     * @param appointmentDateStart Fecha de inicio.
     * @param appointmentDateEnd Fecha de fin.
     * @return Lista reactiva de citas dentro del rango.
     */
    Flux<Appointment> findByAppointmentDateBetween(LocalDate appointmentDateStart, LocalDate appointmentDateEnd);

    /**
     * Busca todas las citas entre un paciente y un doctor específicos.
     *
     * @param patientId ID del paciente.
     * @param doctorId ID del doctor.
     * @return Lista reactiva de citas entre ese paciente y ese doctor.
     */
    Flux<Appointment> findByPatientIdAndDoctorId(String patientId, String doctorId);
}
