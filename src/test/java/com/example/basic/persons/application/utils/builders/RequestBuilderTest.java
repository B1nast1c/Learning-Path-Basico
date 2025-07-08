package com.example.basic.persons.application.utils.builders;

import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class RequestBuilderTest {
    private static Doctor doctor;
    private static Patient patient;

    @BeforeAll
    static void init() {
        doctor = Doctor.builder()
            .personID("11111111")
            .personAppID("DOCTOR-11111111")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.DOCTOR)
            .isActive(true)
            .specialization("NEUROLOGY")
            .build();

        patient = Patient.builder()
            .personID("22222222")
            .personAppID("PATIENT-22222222")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.PATIENT)
            .isActive(true)
            .birthDate(LocalDate.of(2002, 5, 26))
            .build();
    }

    @Test
    void testBuildDoctorRequest() {
        DoctorRequest localRequest = DoctorRequest.builder()
            .personID("11111111")
            .personName("NAME")
            .personSurname("SURNAME")
            .specialization("NEUROLOGY")
            .isActive(true)
            .build();
        Doctor result = RequestBuilder.buildDoctorRequest(localRequest);
        Assertions.assertEquals(doctor.getPersonAppID(), result.getPersonAppID());
        Assertions.assertEquals(doctor.getPersonName(), result.getPersonName());
        Assertions.assertEquals(doctor.getPersonSurname(), result.getPersonSurname());
        Assertions.assertEquals(doctor.getSpecialization(), result.getSpecialization());
    }

    @Test
    void testBuildPatientRequest() {
        PatientRequest localRequest = PatientRequest.builder()
            .personID("22222222")
            .personName("NAME")
            .personSurname("SURNAME")
            .birthDate("26/05/2002")
            .isActive(true)
            .build();
        Patient result = RequestBuilder.buildPatientRequest(localRequest);
        Assertions.assertEquals(patient.getPersonAppID(), result.getPersonAppID());
        Assertions.assertEquals(patient.getPersonName(), result.getPersonName());
        Assertions.assertEquals(patient.getPersonSurname(), result.getPersonSurname());
        Assertions.assertEquals(patient.getBirthDate(), result.getBirthDate());
    }
}