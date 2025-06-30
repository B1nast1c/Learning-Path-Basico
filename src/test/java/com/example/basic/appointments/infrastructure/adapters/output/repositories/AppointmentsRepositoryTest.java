package com.example.basic.appointments.infrastructure.adapters.output.repositories;

import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentStatus;
import com.example.basic.appointments.domain.services.AppointmentValidationService;
import com.example.basic.appointments.infrastructure.repositories.AppointmentRepository;
import com.example.basic.exceptions.throwables.DateFormatExc;
import com.example.basic.persons.domain.models.Specializations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AppointmentsRepositoryTest {
    @Mock
    AppointmentRepository testRepository;
    @Mock
    AppointmentValidationService testService;
    @InjectMocks
    AppointmentsRepository testAppointmentsRepository;
    Appointment appointment;

    @BeforeEach
    void setUp() {
         appointment = Appointment.builder()
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
    }

    private void invokeInvalidDateFormat() {
        testAppointmentsRepository.getAppointmentsByDateRange("01-01-2000", "12-12-2012").blockFirst();
    }

    private void invokeErrorSaving() {
        testAppointmentsRepository.saveAppointment(appointment).block();
    }

    @Test
    void testSaveAppointment() {
        Mockito.when(testRepository.save(appointment))
            .thenReturn(Mono.just(appointment));

        Appointment result = testAppointmentsRepository.saveAppointment(appointment).block();

        assert result != null;
        assertEquals(appointment.getPatientFullName(), result.getPatientFullName());
        assertEquals(appointment.getAppointmentId(), result.getAppointmentId());
        assertEquals(appointment.getDoctorFullName(), result.getDoctorFullName());
        assertEquals(appointment.getAppointmentSpeciality(), result.getAppointmentSpeciality());
    }

    @Test
    void testSaveAppointmentError() {
        Mockito.when(testRepository.save(appointment))
            .thenReturn(Mono.error(new Exception("Generic Message")));

        Exception result = assertThrows(Exception.class, this::invokeErrorSaving);
        assertEquals("java.lang.Exception: Generic Message", result.getMessage());
    }

    @Test
    void testUpdateAppointment() {
    }

    @Test
    void testGetAppointmentById() {
        Mockito.when(testRepository.findById(Mockito.anyString()))
            .thenReturn(Mono.just(appointment));

        Appointment result = testAppointmentsRepository.getAppointmentById("APT-TEST").block();

        assert result != null;
        assertEquals(appointment.getPatientFullName(), result.getPatientFullName());
        assertEquals(appointment.getAppointmentId(), result.getAppointmentId());
        assertEquals(appointment.getDoctorFullName(), result.getDoctorFullName());
        assertEquals(appointment.getAppointmentSpeciality(), result.getAppointmentSpeciality());
    }

    @Test
    void testGetAppointmentsByPatient() {
        Mockito.when(testRepository.findByPatientId(Mockito.anyString()))
            .thenReturn(Flux.just(appointment));

        Appointment result = testAppointmentsRepository.getAppointmentsByPatient("PATIENT-ID").blockFirst();

        assert result != null;
        assertEquals(appointment.getPatientFullName(), result.getPatientFullName());
        assertEquals(appointment.getAppointmentId(), result.getAppointmentId());
        assertEquals(appointment.getDoctorFullName(), result.getDoctorFullName());
        assertEquals(appointment.getAppointmentSpeciality(), result.getAppointmentSpeciality());
    }

    @Test
    void testGetAppointmentsByDoctor() {
        Mockito.when(testRepository.findByDoctorId(Mockito.anyString()))
            .thenReturn(Flux.just(appointment));

        Appointment result = testAppointmentsRepository.getAppointmentsByDoctor("DOCTOR-ID").blockFirst();

        assert result != null;
        assertEquals(appointment.getPatientFullName(), result.getPatientFullName());
        assertEquals(appointment.getAppointmentId(), result.getAppointmentId());
        assertEquals(appointment.getDoctorFullName(), result.getDoctorFullName());
        assertEquals(appointment.getAppointmentSpeciality(), result.getAppointmentSpeciality());
    }

    @Test
    void testGetAppointmentsByDateRange() {
        Mockito.when(testRepository.findByAppointmentDateBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
            .thenReturn(Flux.just(appointment));

        Appointment result = testAppointmentsRepository.getAppointmentsByDateRange("01/01/2000", "12/12/2020").blockFirst();
        assert result != null;
        assertEquals(appointment.getPatientFullName(), result.getPatientFullName());
        assertEquals(appointment.getAppointmentId(), result.getAppointmentId());
        assertEquals(appointment.getDoctorFullName(), result.getDoctorFullName());
        assertEquals(appointment.getAppointmentSpeciality(), result.getAppointmentSpeciality());
    }

    @Test
    void testGetAppointmentsByInvalidDateRange() {
        DateFormatExc result = assertThrows(DateFormatExc.class, this::invokeInvalidDateFormat);
        assertEquals("Invalid date format: Invalid date format, must be dd/MM/YYYY", result.getMessage());
    }

    @Test
    void testGetAppointmentsByPersons() {
        Mockito.when(testRepository.findByPatientIdAndDoctorId(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Flux.just(appointment));

        Appointment result = testAppointmentsRepository.getAppointmentsByPersons("PATIENT-ID", "DOCTOR-ID").blockFirst();

        assert result != null;
        assertEquals(appointment.getPatientFullName(), result.getPatientFullName());
        assertEquals(appointment.getAppointmentId(), result.getAppointmentId());
        assertEquals(appointment.getDoctorFullName(), result.getDoctorFullName());
        assertEquals(appointment.getAppointmentSpeciality(), result.getAppointmentSpeciality());
    }
}