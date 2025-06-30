package com.example.basic.persons.insfrastructure.adapters.input.controllers;

import com.example.basic.persons.application.ports.input.cases.PatientUseCase;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PatientControllerTest {
    @Mock
    PatientUseCase patientUseCase;
    @InjectMocks
    PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientById() {
        when(patientUseCase.getPatient(anyString())).thenReturn(null);

        Mono<GenericResponse> result = patientController.getPatientById("patientId");
        Assertions.assertNull(result);
    }

    @Test
    void testGetPatientsByFullName() {
        when(patientUseCase.getPatientsByName(anyString(), anyString())).thenReturn(null);

        Mono<GenericResponse> result = patientController.getPatientsByFullName("name", "surname");
        Assertions.assertNull(result);
    }

    @Test
    void testGetPatientsByBirthDate() {
        when(patientUseCase.getPatientsByDate(anyString(), anyString())).thenReturn(null);

        Mono<GenericResponse> result = patientController.getPatientsByBirthDate("initDate", "endDate");
        Assertions.assertNull(result);
    }

    @Test
    void testCreatePatient() {
        when(patientUseCase.createPatient(any(PatientRequest.class))).thenReturn(null);

        Mono<GenericResponse> result = patientController.createPatient(PatientRequest.builder().build());
        Assertions.assertNull(result);
    }

    @Test
    void testRemovePatient() {
        when(patientUseCase.deletePatient(anyString())).thenReturn(null);

        Mono<GenericResponse> result = patientController.removePatient("patientId");
        Assertions.assertNull(result);
    }
}