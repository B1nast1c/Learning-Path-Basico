package com.example.basic.appointments.application.services;

import com.example.basic.appointments.application.ports.input.cases.AppointmentUseCases;
import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import com.example.basic.appointments.application.ports.output.repository.AppointmentRepositoryInterface;
import com.example.basic.appointments.application.utils.AppointmentMapper;
import com.example.basic.appointments.application.utils.Constants;
import com.example.basic.appointments.application.utils.builders.ResponseBuilder;
import com.example.basic.appointments.domain.models.Appointment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Servicio que implementa las acciones relacionadas con las citas médicas.
 * Se encarga de coordinar la lógica entre la capa de entrada (controladores o casos de uso)
 * y la capa de acceso a datos (repositorio).
 */
@Slf4j
@Service
public class AppointmentService implements AppointmentUseCases {

    private final AppointmentRepositoryInterface appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    /**
     * Constructor del servicio de citas.
     *
     * @param repositoryInterface Repositorio para acceder a los datos de citas.
     * @param mapper Herramienta para convertir objetos entre capas.
     */
    public AppointmentService(
        AppointmentRepositoryInterface repositoryInterface,
        AppointmentMapper mapper) {
        this.appointmentRepository = repositoryInterface;
        this.appointmentMapper = mapper;
    }

    /**
     * Crea una nueva cita médica.
     *
     * @param appointment Datos de la cita.
     * @return Respuesta con los detalles de la cita creada.
     */
    @Override
    public Mono<AppointmentsResponse> createAppointment(Appointment appointment) {
        return ResponseBuilder.buildSavedAppointmentResponse(appointmentRepository.saveAppointment(appointment));
    }

    /**
     * Cancela una cita médica usando su ID.
     *
     * @param appointmentId ID de la cita a cancelar.
     * @return Respuesta indicando que la cita fue cancelada.
     */
    @Override
    public Mono<GenericResponse> cancelAppointment(String appointmentId) {
        return appointmentRepository.updateAppointment(appointmentId)
            .thenReturn(GenericResponse.builder()
                .responseStatus(Constants.VALID)
                .details("Canceling appointment")
                .data(List.of())
                .build());
    }

    /**
     * Obtiene los detalles de una cita específica.
     *
     * @param appointmentId ID de la cita.
     * @return Respuesta con la información de la cita.
     */
    @Override
    public Mono<GenericResponse> getAppointmentDetails(String appointmentId) {
        return ResponseBuilder.buildSingleResponse(
            appointmentRepository.getAppointmentById(appointmentId),
            appointmentMapper::mapAppointmentToResponse
        );
    }

    /**
     * Obtiene todas las citas de un paciente.
     *
     * @param patientId ID del paciente.
     * @return Lista de citas del paciente.
     */
    @Override
    public Mono<GenericResponse> getPatientAppointments(String patientId) {
        return ResponseBuilder.buildListResponse(
            appointmentRepository.getAppointmentsByPatient(patientId),
            appointmentMapper::mapAppointmentsToResponse
        );
    }

    /**
     * Obtiene todas las citas de un doctor.
     *
     * @param doctorId ID del doctor.
     * @return Lista de citas del doctor.
     */
    @Override
    public Mono<GenericResponse> getDoctorAppointments(String doctorId) {
        return ResponseBuilder.buildListResponse(
            appointmentRepository.getAppointmentsByDoctor(doctorId),
            appointmentMapper::mapAppointmentsToResponse
        );
    }

    /**
     * Obtiene todas las citas dentro de un rango de fechas.
     *
     * @param initDate Fecha de inicio (formato: YYYY/MM/DD).
     * @param endDate Fecha de fin (formato: YYYY/MM/DD).
     * @return Lista de citas en ese rango.
     */
    @Override
    public Mono<GenericResponse> getAppointmentsIn(String initDate, String endDate) {
        return ResponseBuilder.buildListResponse(
            appointmentRepository.getAppointmentsByDateRange(initDate, endDate),
            appointmentMapper::mapAppointmentsToResponse
        );
    }

    /**
     * Busca una cita específica entre un paciente y un doctor.
     *
     * @param patientId ID del paciente.
     * @param doctorId ID del doctor.
     * @return Lista de citas entre ese paciente y ese doctor.
     */
    @Override
    public Mono<GenericResponse> getSpecificAppointment(String patientId, String doctorId) {
        return ResponseBuilder.buildListResponse(
            appointmentRepository.getAppointmentsByPersons(patientId, doctorId),
            appointmentMapper::mapAppointmentsToResponse
        );
    }
}
