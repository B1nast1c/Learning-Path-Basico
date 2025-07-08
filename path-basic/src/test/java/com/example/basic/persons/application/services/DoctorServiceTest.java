package com.example.basic.persons.application.services;

import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.models.PersonsResponse;
import com.example.basic.persons.application.ports.ouput.repository.DoctorRepositoryInterface;
import com.example.basic.persons.application.utils.PersonMapper;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @Mock
    DoctorRepositoryInterface testRepository;
    @Mock
    PersonMapper testMapper;
    @InjectMocks
    DoctorService testService;
    Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = Doctor.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.DOCTOR)
            .isActive(true)
            .specialization("NEUROLOGY")
            .build();
    }

    @Test
    void testGetDoctors() {
        Mockito.when(testRepository.findAll()).thenReturn(Flux.just(doctor));
        Mockito.when(testMapper.mapDoctorsToResponse(List.of(doctor)))
            .thenReturn(List.of(PersonsResponse.builder()
                .personID("ID")
                .personName("NAME")
                .personSurname("SURNAME")
                .build()));

        GenericResponse result = testService.getDoctors().block();

        assertNotNull(result);
        assertEquals(doctor.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getData().get(0).getPersonID());

    }

    @Test
    void testGetDoctorsBySpeciality() {
        Mockito.when(testRepository.findBySpeciality(Mockito.anyString()))
            .thenReturn(Flux.just(doctor));
        Mockito.when(testMapper.mapDoctorsToResponse(List.of(doctor)))
            .thenReturn(List.of(PersonsResponse.builder()
                .personID("ID")
                .personName("NAME")
                .personSurname("SURNAME")
                .build()));

        GenericResponse result = testService.getDoctorsBySpeciality("NEUROLOGY").block();

        assertNotNull(result);
        assertEquals(doctor.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getData().get(0).getPersonID());
    }

    @Test
    void testGetDoctor() {
        Mockito.when(testRepository.findById(Mockito.anyString()))
            .thenReturn(Mono.just(doctor));
        Mockito.when(testMapper.mapDoctorToResponse(doctor))
            .thenReturn(PersonsResponse.builder()
                .personID("ID")
                .personName("NAME")
                .personSurname("SURNAME")
                .build());

        GenericResponse result = testService.getDoctor("ID").block();

        assertNotNull(result);
        assertEquals(doctor.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getData().get(0).getPersonID());
    }

    @Test
    void testGetDoctorByFullName() {
        Mockito.when(testRepository.findByFullName(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Flux.just(doctor));
        Mockito.when(testMapper.mapDoctorsToResponse(List.of(doctor)))
            .thenReturn(List.of(PersonsResponse.builder()
                .personID("ID")
                .personName("NAME")
                .personSurname("SURNAME")
                .build()));

        GenericResponse result = testService.getDoctorByFullName("NAME","SURNAME").block();

        assertNotNull(result);
        assertEquals(doctor.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getData().get(0).getPersonID());
    }

    @Test
    void testCreateDoctor() {
        Mockito.when(testRepository.createDoctor(Mockito.any(Doctor.class)))
            .thenReturn(Mono.just(doctor));
        Mockito.when(testMapper.mapDoctorToResponse(doctor))
            .thenReturn(PersonsResponse.builder()
                .personID("ID")
                .personName("NAME")
                .personSurname("SURNAME")
                .build());

        DoctorRequest request = DoctorRequest.builder()
            .personID("ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .specialization("NEUROLOGY")
            .build();
        GenericResponse result = testService.createDoctor(request).block();

        assertNotNull(result);
        assertEquals(doctor.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getData().get(0).getPersonID());
    }

    @Test
    void testRemoveDoctor() {
        Mockito.when(testRepository.deleteDoctor(Mockito.anyString()))
            .thenReturn(Mono.just(doctor));
        Mockito.when(testMapper.mapDoctorToResponse(doctor))
            .thenReturn(PersonsResponse.builder()
                .personID("ID")
                .personName("NAME")
                .personSurname("SURNAME")
                .build());

        GenericResponse result = testService.removeDoctor("ID").block();

        assertNotNull(result);
        assertEquals(doctor.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(doctor.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(doctor.getPersonID(), result.getData().get(0).getPersonID());
    }
}
