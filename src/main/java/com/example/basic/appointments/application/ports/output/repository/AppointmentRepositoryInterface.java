package com.example.basic.appointments.application.ports.output.repository;

import com.example.basic.appointments.domain.models.Appointment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Esta interfaz define las operaciones que se pueden hacer con las citas en la base de datos.
 */
public interface AppointmentRepositoryInterface {

    /**
     * Guarda una nueva cita en la base de datos.
     *
     * @param appointment Datos de la cita que se quiere guardar.
     * @return La cita guardada.
     */
    Mono<Appointment> saveAppointment(Appointment appointment);

    /**
     * Actualiza una cita existente usando su ID.
     *
     * @param appointmentId ID de la cita que se quiere actualizar.
     * @return La cita actualizada.
     */
    Mono<Appointment> updateAppointment(String appointmentId);

    /**
     * Busca una cita por su ID.
     *
     * @param appointmentId ID de la cita.
     * @return La cita encontrada, si existe.
     */
    Mono<Appointment> getAppointmentById(String appointmentId);

    /**
     * Obtiene todas las citas de un paciente.
     *
     * @param patientId ID del paciente.
     * @return Lista de citas del paciente.
     */
    Flux<Appointment> getAppointmentsByPatient(String patientId);

    /**
     * Obtiene todas las citas de un doctor.
     *
     * @param doctorId ID del doctor.
     * @return Lista de citas del doctor.
     */
    Flux<Appointment> getAppointmentsByDoctor(String doctorId);

    /**
     * Obtiene todas las citas dentro de un rango de fechas.
     *
     * @param initDate Fecha de inicio (formato: YYYY/MM/DD).
     * @param endDate Fecha de fin (formato: YYYY/MM/DD).
     * @return Lista de citas dentro del rango de fechas.
     */
    Flux<Appointment> getAppointmentsByDateRange(String initDate, String endDate);

    /**
     * Busca citas entre un paciente y un doctor específicos.
     *
     * @param patientId ID del paciente.
     * @param doctorId ID del doctor.
     * @return Lista de citas entre ese paciente y ese doctor.
     */
    Flux<Appointment> getAppointmentsByPersons(String patientId, String doctorId);
}
