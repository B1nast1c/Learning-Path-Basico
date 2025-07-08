package com.example.basic.appointments.infrastructure.repositories;

import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentStatus;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DataMongoTest
class AppointmentRepositoryTest {
    @Autowired
    AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        Appointment appointment = Appointment.builder()
            .appointmentId("APT-TEST")
            .requestId("REQ-TEST")
            .patientId("PATIENT-ID")
            .patientFullName("PATIENT")
            .doctorId("DOCTOR-ID")
            .doctorFullName("DOCTOR")
            .appointmentDate(LocalDateTime.now())
            .appointmentSpeciality(String.valueOf(Specializations.OBSTETRICS))
            .appointmentStatus(AppointmentStatus.ACTIVE)
            .build();

        appointmentRepository.deleteAll()
            .then(appointmentRepository.save(appointment))
            .block();
    }

    @Test
    void testFindByPatientId() {
        StepVerifier.create(appointmentRepository.findByPatientId("PATIENT-ID"))
            .expectNextCount(1)
            .verifyComplete();
    }

    @Test
    void testFindByDoctorId() {
        StepVerifier.create(appointmentRepository.findByDoctorId("PATIENT-ID"))
            .verifyComplete();
    }

    @Test
    void testFindByAppointmentDateBetween() {
        LocalDate start = LocalDate.of(2025, 6, 28);
        LocalDate end = LocalDate.of(2025, 6, 30);

        StepVerifier.create(appointmentRepository.findByAppointmentDateBetween(start, end))
            .verifyComplete();
    }

    @Test
    void testFindByPatientIdAndDoctorId() {
        StepVerifier.create(appointmentRepository.findByPatientIdAndDoctorId("PATIENT-ID", "DOCTOR-ID"))
            .expectNextMatches(app -> app.getDoctorId().equals("DOCTOR-ID") && app.getPatientId().equals("PATIENT-ID"))
            .verifyComplete();
    }
}


