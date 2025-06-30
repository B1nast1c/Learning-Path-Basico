package com.example.basic.persons.application.utils;

import com.example.basic.persons.application.ports.ouput.models.PersonsResponse;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {

    private PersonMapper personMapper;
    private static Patient patient;
    private static Doctor doctor;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        personMapper = new PersonMapper(modelMapper);

        patient = Patient.builder()
            .personID("ID")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.PATIENT)
            .isActive(true)
            .birthDate(LocalDate.now())
            .build();

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
    void testMapPatientToResponse() {
        PersonsResponse response = personMapper.mapPatientToResponse(patient);

        assertEquals("NAME", response.getPersonName());
        assertTrue(response.getPersonDetails().contains("BIRTHDATE"));
    }

    @Test
    void testMapDoctorToResponse() {
        PersonsResponse response = personMapper.mapDoctorToResponse(doctor);

        assertEquals("NAME", response.getPersonName());
        assertTrue(response.getPersonDetails().contains("SPECIALITY"));
    }

    @Test
    void testMapPatientsToResponse() {
        List<PersonsResponse> responses = personMapper.mapPatientsToResponse(List.of(patient));

        assertEquals(1, responses.size());
        assertEquals("NAME", responses.get(0).getPersonName());
        assertTrue(responses.get(0).getPersonDetails().contains("BIRTHDATE"));
    }

    @Test
    void testMapDoctorsToResponse() {
        List<PersonsResponse> responses = personMapper.mapDoctorsToResponse(List.of(doctor));

        assertEquals(1, responses.size());
        assertEquals("NAME", responses.get(0).getPersonName());
        assertTrue(responses.get(0).getPersonDetails().contains("SPECIALITY"));
    }
}
