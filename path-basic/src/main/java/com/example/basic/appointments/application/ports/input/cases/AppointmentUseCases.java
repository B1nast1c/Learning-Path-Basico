package com.example.basic.appointments.application.ports.input.cases;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import com.example.basic.appointments.domain.models.Appointment;
import reactor.core.publisher.Mono;

/**
 * Esta interfaz define las acciones principales que se pueden hacer con las citas médicas.
 */
public interface AppointmentUseCases {

    /**
     * Crea una nueva cita médica.
     *
     * @param appointment Datos de la cita.
     * @return Respuesta con los detalles de la cita creada.
     */
    Mono<AppointmentsResponse> createAppointment(Appointment appointment);

    /**
     * Cancela una cita médica usando su ID.
     *
     * @param appointmentId ID de la cita que se quiere cancelar.
     * @return Respuesta indicando si la cancelación fue exitosa.
     */
    Mono<GenericResponse> cancelAppointment(String appointmentId);

    /**
     * Obtiene los detalles de una cita específica.
     *
     * @param appointmentId ID de la cita.
     * @return Respuesta con la información de la cita.
     */
    Mono<GenericResponse> getAppointmentDetails(String appointmentId);

    /**
     * Obtiene todas las citas de un paciente.
     *
     * @param patientId ID del paciente.
     * @return Respuesta con la lista de citas del paciente.
     */
    Mono<GenericResponse> getPatientAppointments(String patientId);

    /**
     * Obtiene todas las citas de un doctor.
     *
     * @param doctorId ID del doctor.
     * @return Respuesta con la lista de citas del doctor.
     */
    Mono<GenericResponse> getDoctorAppointments(String doctorId);

    /**
     * Obtiene todas las citas dentro de un rango de fechas.
     *
     * @param initDate Fecha de inicio (formato esperado: YYYY/MM/DD).
     * @param endDate Fecha de fin (formato esperado: YYYY/MM/DD).
     * @return Respuesta con la lista de citas en ese rango.
     */
    Mono<GenericResponse> getAppointmentsIn(String initDate, String endDate);

    /**
     * Busca una cita específica entre un paciente y un doctor.
     *
     * @param patientId ID del paciente.
     * @param doctorId ID del doctor.
     * @return Respuesta con los detalles de la cita si existe.
     */
    Mono<GenericResponse> getSpecificAppointment(String patientId, String doctorId);
}
