package com.example.basic.appointments.application.ports.input.cases;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import com.example.basic.appointments.domain.models.Appointment;
import reactor.core.publisher.Mono;

public interface AppointmentUseCases {
    Mono<AppointmentsResponse> createAppointment(Appointment appointment);
    Mono<GenericResponse> cancelAppointment(String appointmentId);
    Mono<GenericResponse> getAppointmentDetails(String appointmentId);
    Mono<GenericResponse> getPatientAppointments(String patientId);
    Mono<GenericResponse> getDoctorAppointments(String doctorId);
    Mono<GenericResponse> getAppointmentsIn(String initDate, String endDate);
    Mono<GenericResponse> getSpecificAppointment(String patientId, String doctorId);
}
