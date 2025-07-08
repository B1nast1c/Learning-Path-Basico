package com.example.basic.persons.insfrastructure.adapters.input.controllers;

import com.example.basic.persons.application.ports.input.cases.DoctorUseCase;
import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
class DoctorControllerTest {
    @Mock
    DoctorUseCase doctorUseCase;
    @InjectMocks
    DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDoctors(){
        when(doctorUseCase.getDoctors()).thenReturn(null);

        Mono<GenericResponse> result = doctorController.getDoctors();
        Assertions.assertNull(result);
    }

    @Test
    void testGetDoctorsBySpeciality(){
        when(doctorUseCase.getDoctorsBySpeciality(anyString())).thenReturn(null);

        Mono<GenericResponse> result = doctorController.getDoctorsBySpeciality("speciality");
        Assertions.assertNull(result);
    }

    @Test
    void testGetDoctorsById(){
        when(doctorUseCase.getDoctor(anyString())).thenReturn(null);

        Mono<GenericResponse> result = doctorController.getDoctorsById("doctorId");
        Assertions.assertNull(result);
    }

    @Test
    void testGetDoctorsByFullName(){
        when(doctorUseCase.getDoctorByFullName(anyString(), anyString())).thenReturn(null);

        Mono<GenericResponse> result = doctorController.getDoctorsByFullName("name", "surname");
        Assertions.assertNull(result);
    }

    @Test
    void testCreateDoctorWithRequest(){
        when(doctorUseCase.createDoctor(any(DoctorRequest.class))).thenReturn(null);

        Mono<GenericResponse> result = doctorController.createDoctor(DoctorRequest.builder().build());
        Assertions.assertNull(result);
    }

    @Test
    void testRemoveDoctor(){
        when(doctorUseCase.removeDoctor(anyString())).thenReturn(null);

        Mono<GenericResponse> result = doctorController.removeDoctor("doctorId");
        Assertions.assertNull(result);
    }
}