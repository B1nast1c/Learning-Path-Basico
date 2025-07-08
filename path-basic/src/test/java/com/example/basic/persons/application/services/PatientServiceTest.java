package com.example.basic.persons.application.services;

import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.models.PersonsResponse;
import com.example.basic.persons.application.ports.ouput.repository.PatientRepositoryInterface;
import com.example.basic.persons.application.utils.PersonMapper;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Patient;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    PatientRepositoryInterface testInterface;
    @Mock
    PersonMapper testMapper;
    @InjectMocks
    PatientService testService;
    Patient patient;

    @BeforeEach
    void setUp() {
        patient = Patient.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.PATIENT)
            .isActive(true)
            .birthDate(LocalDate.now())
            .build();
    }

    @Test
    void testGetPatient() {
        when(testInterface.findById(Mockito.anyString())).thenReturn(Mono.just(patient));
        when(testMapper.mapPatientToResponse(patient))
            .thenReturn(PersonsResponse.builder()
                .personName("NAME")
                .personSurname("SURNAME")
                .personID("ID")
                .build());

        GenericResponse result = testService.getPatient("ID").block();

        assertNotNull(result);
        assertEquals(patient.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(patient.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(patient.getPersonID(), result.getData().get(0).getPersonID());
    }

    @Test
    void testGetPatientsByName() {
        when(testInterface.findByFullName(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Flux.just(patient));
        when(testMapper.mapPatientsToResponse(List.of(patient)))
            .thenReturn(List.of(PersonsResponse.builder()
                .personName("NAME")
                .personSurname("SURNAME")
                .personID("ID")
                .build()));

        GenericResponse result = testService.getPatientsByName("NAME", "SURNAME").block();

        assertNotNull(result);
        assertEquals(patient.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(patient.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(patient.getPersonID(), result.getData().get(0).getPersonID());
    }

    @Test
    void testGetPatientsByDate() {
        when(testInterface.findByBirthDate(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Flux.just(patient));
        when(testMapper.mapPatientsToResponse(List.of(patient)))
            .thenReturn(List.of(PersonsResponse.builder()
                .personName("NAME")
                .personSurname("SURNAME")
                .personID("ID")
                .build()));

        GenericResponse result = testService.getPatientsByDate("01-01-2000", "12-12-2020").block();

        assertNotNull(result);
        assertEquals(patient.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(patient.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(patient.getPersonID(), result.getData().get(0).getPersonID());
    }

    @Test
    void testCreatePatient() {
        when(testInterface.createPatient(Mockito.any(PatientRequest.class))).thenReturn(Mono.just(patient));
        when(testMapper.mapPatientToResponse(patient))
            .thenReturn(PersonsResponse.builder()
                .personName("NAME")
                .personSurname("SURNAME")
                .personID("ID")
                .build());

        PatientRequest request = PatientRequest.builder()
            .personID("ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .build();
        GenericResponse result = testService.createPatient(request).block();

        assertNotNull(result);
        assertEquals(patient.getPersonName(), result.getData().get(0).getPersonName());
        assertEquals(patient.getPersonSurname(), result.getData().get(0).getPersonSurname());
        assertEquals(patient.getPersonID(), result.getData().get(0).getPersonID());
    }
}