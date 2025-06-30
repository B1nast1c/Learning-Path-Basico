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

@Slf4j
@Service
public class AppointmentService implements AppointmentUseCases {
    private final AppointmentRepositoryInterface appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(
        AppointmentRepositoryInterface repositoryInterface,
        AppointmentMapper mapper) {
        this.appointmentRepository = repositoryInterface;
        this.appointmentMapper = mapper;
    }

    @Override
    public Mono<AppointmentsResponse> createAppointment(Appointment appointment) {
        return ResponseBuilder.buildSavedAppointmentResponse(appointmentRepository.saveAppointment(appointment));
    }

    @Override
    public Mono<GenericResponse> cancelAppointment(String appointmentId) {
        return appointmentRepository.updateAppointment(appointmentId)
            .thenReturn(GenericResponse.builder()
                .responseStatus(Constants.VALID)
                .details("Canceling appointment")
                .data(List.of())
                .build());
    }

    @Override
    public Mono<GenericResponse> getAppointmentDetails(String appointmentId) {
        return ResponseBuilder.buildSingleResponse(appointmentRepository.getAppointmentById(appointmentId), appointmentMapper::mapAppointmentToResponse);
    }

    @Override
    public Mono<GenericResponse> getPatientAppointments(String patientId) {
        return ResponseBuilder.buildListResponse(appointmentRepository.getAppointmentsByPatient(patientId), appointmentMapper::mapAppointmentsToResponse);
    }

    @Override
    public Mono<GenericResponse> getDoctorAppointments(String doctorId) {
        return ResponseBuilder.buildListResponse(appointmentRepository.getAppointmentsByDoctor(doctorId), appointmentMapper::mapAppointmentsToResponse);
    }

    @Override
    public Mono<GenericResponse> getAppointmentsIn(String initDate, String endDate) {
        return ResponseBuilder.buildListResponse(appointmentRepository.getAppointmentsByDateRange(initDate, endDate), appointmentMapper::mapAppointmentsToResponse);
    }

    @Override
    public Mono<GenericResponse> getSpecificAppointment(String patientId, String doctorId) {
        return ResponseBuilder.buildListResponse(appointmentRepository.getAppointmentsByPersons(patientId, doctorId), appointmentMapper::mapAppointmentsToResponse);
    }
}
