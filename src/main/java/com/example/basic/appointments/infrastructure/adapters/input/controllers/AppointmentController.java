package com.example.basic.appointments.infrastructure.adapters.input.controllers;

import com.example.basic.appointments.application.ports.input.cases.AppointmentRequestUseCases;
import com.example.basic.appointments.application.ports.input.cases.AppointmentUseCases;
import com.example.basic.appointments.application.ports.input.models.RequestModel;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import reactor.core.publisher.Mono;

/**
 * Controlador REST que expone los endpoints relacionados con las citas médicas.
 * Permite crear solicitudes de cita, consultar detalles, cancelar citas y obtener citas
 * por paciente, doctor o rango de fechas.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRequestUseCases appointmentRequestUseCases;
    private final AppointmentUseCases appointmentUseCases;

    /**
     * Constructor del controlador.
     *
     * @param requestUseCases Casos de uso para solicitudes de cita.
     * @param appointmentUseCases Casos de uso para gestión de citas.
     */
    public AppointmentController(
        AppointmentRequestUseCases requestUseCases,
        AppointmentUseCases appointmentUseCases) {
        this.appointmentRequestUseCases = requestUseCases;
        this.appointmentUseCases = appointmentUseCases;
    }

    /**
     * Crea una nueva solicitud de cita.
     *
     * @param request Datos de la solicitud.
     * @return Respuesta con el resultado de la operación.
     */
    @PostMapping("/request")
    public Mono<GenericResponse> requestAppointment(@RequestBody RequestModel request) {
        return appointmentRequestUseCases.createRequest(request);
    }

    /**
     * Obtiene los detalles de una cita específica.
     *
     * @param appointmentId ID de la cita.
     * @return Detalles de la cita.
     */
    @GetMapping("/detail/{appointmentId}")
    public Mono<GenericResponse> getAppointmentDetail(@PathVariable String appointmentId) {
        return appointmentUseCases.getAppointmentDetails(appointmentId);
    }

    /**
     * Obtiene todas las citas de un doctor.
     *
     * @param doctorId ID del doctor.
     * @return Lista de citas del doctor.
     */
    @GetMapping("/doctor/{doctorId}")
    public Mono<GenericResponse> getAppointmentsDetailByDoctor(@PathVariable String doctorId) {
        return appointmentUseCases.getDoctorAppointments(doctorId);
    }

    /**
     * Obtiene todas las citas de un paciente.
     *
     * @param patientId ID del paciente.
     * @return Lista de citas del paciente.
     */
    @GetMapping("/patient/{patientId}")
    public Mono<GenericResponse> getAppointmentsDetailByPatient(@PathVariable String patientId) {
        return appointmentUseCases.getPatientAppointments(patientId);
    }

    /**
     * Obtiene todas las citas dentro de un rango de fechas.
     *
     * @param initDate Fecha de inicio (formato: YYYY-MM-DD).
     * @param endDate Fecha de fin (formato: YYYY-MM-DD).
     * @return Lista de citas en ese rango.
     */
    @GetMapping("/range")
    public Mono<GenericResponse> getAppointmentsBetween(@RequestParam String initDate, @RequestParam String endDate) {
        return appointmentUseCases.getAppointmentsIn(initDate, endDate);
    }

    /**
     * Obtiene una cita específica entre un paciente y un doctor.
     *
     * @param patientId ID del paciente.
     * @param doctorId ID del doctor.
     * @return Detalles de la cita si existe.
     */
    @GetMapping("/detailsBy")
    public Mono<GenericResponse> getAppointmentsPersons(@RequestParam String patientId, @RequestParam String doctorId) {
        return appointmentUseCases.getSpecificAppointment(patientId, doctorId);
    }

    /**
     * Cancela una cita médica.
     *
     * @param appointmentId ID de la cita a cancelar.
     * @return Respuesta indicando si la cancelación fue exitosa.
     */
    @PutMapping("/cancel/{appointmentId}")
    public Mono<GenericResponse> cancelAppointment(@PathVariable String appointmentId) {
        return appointmentUseCases.cancelAppointment(appointmentId);
    }
}