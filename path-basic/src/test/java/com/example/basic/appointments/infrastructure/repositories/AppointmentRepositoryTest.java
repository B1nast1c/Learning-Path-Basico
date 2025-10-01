package com.example.basic.appointments.infrastructure.repositories;

import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentStatus;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@DataMongoTest
@ExtendWith(MockitoExtension.class)
class AppointmentRepositoryTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    private Appointment appointmentTest1;
    private Appointment appointmentTest2;

    @BeforeEach
    void setUp() {
        appointmentTest1 = Appointment.builder()
                .appointmentId("APT-TEST-1")
                .requestId("REQ-TEST-1")
                .patientId("PATIENT-ID-1")
                .patientFullName("Alice")
                .doctorId("DOC-ID-1")
                .doctorFullName("Dr. One")
                .appointmentDate(LocalDateTime.of(2024, 1, 10, 9, 0))
                .appointmentSpeciality(String.valueOf(Specializations.CARDIOLOGY))
                .appointmentStatus(AppointmentStatus.ACTIVE)
                .build();

        appointmentTest2 = Appointment.builder()
                .appointmentId("APT-TEST-2")
                .requestId("REQ-TEST-2")
                .patientId("PATIENT-ID-2")
                .patientFullName("Bob")
                .doctorId("DOC-ID-2")
                .doctorFullName("Dr. Two")
                .appointmentDate(LocalDateTime.of(2024, 2, 20, 11, 30))
                .appointmentSpeciality(String.valueOf(Specializations.PEDIATRICS))
                .appointmentStatus(AppointmentStatus.ACTIVE)
                .build();
    }

    @Test
    void findByPatientIdMatchingPatient() {
        Mockito.when(appointmentRepository.findByPatientId("PATIENT-ID-1"))
                .thenReturn(Flux.just(appointmentTest1));

        StepVerifier.create(appointmentRepository.findByPatientId("PATIENT-ID-1"))
                .expectNextMatches(a -> a.getPatientId().equals("PATIENT-ID-1") && a.getAppointmentId().equals("APT-TEST-1"))
                .verifyComplete();
    }

    @Test
    void findByDoctorIdMatchingDoctor() {
        Mockito.when(appointmentRepository.findByDoctorId("DOC-ID-2"))
                .thenReturn(Flux.just(appointmentTest2));

        StepVerifier.create(appointmentRepository.findByDoctorId("DOC-ID-2"))
                .expectNextMatches(a -> a.getDoctorId().equals("DOC-ID-2") && a.getAppointmentId().equals("APT-TEST-2"))
                .verifyComplete();
    }

    @Test
    void findByAppointmentDateBetweenAppointmentsInRange() {
        Mockito.when(appointmentRepository.findByAppointmentDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Flux.just(appointmentTest1, appointmentTest2));

        StepVerifier
                .create(appointmentRepository.findByAppointmentDateBetween(LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 12, 31)))
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findByPatientIdAndDoctorIdEmpty() {
        Mockito.when(appointmentRepository.findByPatientIdAndDoctorId("PATIENT-ID-1", "DOC-ID-2"))
                .thenReturn(Flux.empty());

        StepVerifier.create(appointmentRepository.findByPatientIdAndDoctorId("PATIENT-ID-1", "DOC-ID-2"))
                .verifyComplete();
    }
}
