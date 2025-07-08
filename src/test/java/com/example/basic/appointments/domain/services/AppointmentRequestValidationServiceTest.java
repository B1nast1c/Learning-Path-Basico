package com.example.basic.appointments.domain.services;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.exceptions.throwables.*;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppointmentRequestValidationServiceTest {
    AppointmentRequestValidationService service = new AppointmentRequestValidationService();
    static Doctor doctor;
    static Patient patient;
    static AppointmentRequest appointmentRequest;

    @BeforeEach
    void init() {
        doctor = Doctor.builder()
            .personID("11111111")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.DOCTOR)
            .isActive(true)
            .specialization("NEUROLOGY")
            .build();

        patient = Patient.builder()
            .personID("22222222")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.PATIENT)
            .isActive(true)
            .birthDate(LocalDate.now())
            .build();

        appointmentRequest = AppointmentRequest.builder()
            .requestId("REQ-TEST")
            .patientIdRequest("PATIENT-ID")
            .patientFullName("PATIENT")
            .doctorIdRequest("DOCTOR-ID")
            .doctorFullName("DOCTOR")
            .requestDate(LocalDateTime.of(2025, 12, 12, 12, 0, 0))
            .isValid(true)
            .requestDetail("")
            .requestSpeciality(Specializations.NEUROLOGY)
            .build();
    }

    private void invokeSameDoctorPatient() {
        appointmentRequest.setDoctorIdRequest("ID");
        appointmentRequest.setPatientIdRequest("ID");
        service.validate(
            appointmentRequest,
            Mono.just(doctor),
            Mono.just(patient),
            Flux.empty()
        ).block();
    }

    private void invokeNotFoundPatient() {
        service.validate(
            appointmentRequest,
            Mono.just(doctor),
            Mono.empty(),
            Flux.empty()
        ).block();
    }

    private void invokeNotFoundDoctor() {
        service.validate(
            appointmentRequest,
            Mono.empty(),
            Mono.just(patient),
            Flux.empty()
        ).block();
    }

    private void invokeNotFoundSpeciality() {
        appointmentRequest.setDoctorIdRequest("DOCTOR-ID");
        appointmentRequest.setPatientIdRequest("PATIENT-ID");
        appointmentRequest.setRequestSpeciality(Specializations.EMERGENCY);
        service.validate(
            appointmentRequest,
            Mono.just(doctor),
            Mono.just(patient),
            Flux.empty()
        ).block();
    }

    private void invokeInvalidYear() {
        appointmentRequest.setRequestSpeciality(Specializations.NEUROLOGY);
        appointmentRequest.setRequestDate(LocalDateTime.of(9999, 12, 12, 1, 20, 20));
        service.validate(
            appointmentRequest,
            Mono.just(doctor),
            Mono.just(patient),
            Flux.empty()
        ).block();
    }

    private void invokeInvalidHour() {
        appointmentRequest.setRequestSpeciality(Specializations.NEUROLOGY);
        appointmentRequest.setRequestDate(LocalDateTime.of(2012, 12, 12, 22, 20, 20));
        service.validate(
            appointmentRequest,
            Mono.just(doctor),
            Mono.just(patient),
            Flux.empty()
        ).block();
    }

    private void invokeScheduleTaken() {
        appointmentRequest.setRequestSpeciality(Specializations.NEUROLOGY);
        appointmentRequest.setRequestDate(LocalDateTime.of(2025, 12, 12, 12, 0, 0));
        service.validate(
            appointmentRequest,
            Mono.just(doctor),
            Mono.just(patient),
            Flux.just(appointmentRequest)
        ).block();
    }

    @Test
    void testValidate() {
        AppointmentRequest result = service.validate(appointmentRequest,
            Mono.just(doctor),
            Mono.just(patient),
            Flux.empty()
        ).block();

        assert result != null;
        assertEquals(appointmentRequest.getRequestId(), result.getRequestId());
        assertEquals(appointmentRequest.getDoctorIdRequest(), result.getDoctorIdRequest());
        assertEquals(appointmentRequest.getPatientIdRequest(), result.getPatientIdRequest());
        assertEquals(appointmentRequest.getRequestDate(), result.getRequestDate());
    }

    @Test
    void testValidateError() {
        assertThrows(NotFoundExc.class, this::invokeNotFoundPatient);
        assertThrows(NotFoundExc.class, this::invokeNotFoundDoctor);
        assertThrows(GenericExc.class, this::invokeSameDoctorPatient);
        assertThrows(SpecialityExc.class, this::invokeNotFoundSpeciality);
        assertThrows(DateFormatExc.class, this::invokeInvalidYear);
        assertThrows(DateFormatExc.class, this::invokeInvalidHour);
        assertThrows(DuplicateExc.class, this::invokeScheduleTaken);
    }
}