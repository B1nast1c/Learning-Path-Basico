package com.example.basic.appointments.infrastructure.adapters.input.controllers;

import com.example.basic.appointments.application.ports.input.cases.AppointmentRequestUseCases;
import com.example.basic.appointments.application.ports.input.cases.AppointmentUseCases;
import com.example.basic.appointments.application.ports.input.models.RequestModel;
import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentRequestUseCases appointmentRequestUseCases;
    private final AppointmentUseCases appointmentUseCases;

    public AppointmentController(
        AppointmentRequestUseCases requestUseCases,
        AppointmentUseCases appointmentUseCases) {
        this.appointmentRequestUseCases = requestUseCases;
        this.appointmentUseCases = appointmentUseCases;
    }

    @PostMapping("/request")
    public Mono<GenericResponse> requestAppointment(@RequestBody RequestModel request) {
        return appointmentRequestUseCases.createRequest(request);
    }

    @GetMapping("/detail/{appointmentId}")
    public Mono<GenericResponse> getAppointmentDetail(@PathVariable String appointmentId) {
        return appointmentUseCases.getAppointmentDetails(appointmentId);
    }

    @GetMapping("/doctor/{doctorId}")
    public Mono<GenericResponse> getAppointmentsDetailByDoctor(@PathVariable String doctorId) {
        return appointmentUseCases.getDoctorAppointments(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public Mono<GenericResponse> getAppointmentsDetailByPatient(@PathVariable String patientId) {
        return appointmentUseCases.getPatientAppointments(patientId);
    }

    @GetMapping("/range")
    public Mono<GenericResponse> getAppointmentsBetween(@RequestParam String initDate, @RequestParam String endDate) {
        return appointmentUseCases.getAppointmentsIn(initDate, endDate);
    }

    @GetMapping("/detailsBy")
    public Mono<GenericResponse> getAppointmentsPersons(@RequestParam String patientId, @RequestParam String doctorId) {
        return appointmentUseCases.getSpecificAppointment(patientId, doctorId);
    }

    @PutMapping("/cancel/{appointmentId}")
    public Mono<GenericResponse> cancelAppointment(@PathVariable String appointmentId) {
        return appointmentUseCases.cancelAppointment(appointmentId);
    }
}
