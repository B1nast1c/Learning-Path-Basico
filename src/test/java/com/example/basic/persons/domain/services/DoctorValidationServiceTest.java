package com.example.basic.persons.domain.services;

import com.example.basic.exceptions.throwables.GenericExc;
import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoctorValidationServiceTest {
    DoctorValidationService doctorValidationService = new DoctorValidationService();
    private static Doctor doctor;

    @BeforeAll
    static void init() {
        doctor = Doctor.builder()
            .personID("11111111")
            .personAppID("APP-ID")
            .personName("NAME")
            .personSurname("SURNAME")
            .personRole(AppRoles.DOCTOR)
            .isActive(true)
            .specialization("NEUROLOGY")
            .build();
    }

    @Test
    void testValidate() {
        doctor.setPersonID("12345678");
        doctor.setSpecialization("CARDIOLOGY");
        doctorValidationService.validate(doctor);
    }

    @Test
    void testValidateWrongSpeciality() {
        doctor.setSpecialization("INVALID");
        SpecialityExc exception = assertThrows(SpecialityExc.class, () -> doctorValidationService.validate(doctor));
        assertEquals("Invalid doctor speciality", exception.getMessage());
    }

    @Test
    void testValidateWrongID() {
        doctor.setPersonID("INVALID-ID");
        GenericExc exception = assertThrows(GenericExc.class, () -> doctorValidationService.validate(doctor));
        assertEquals("Invalid doctor ID format", exception.getMessage());
    }

    @Test
    void testValidateSpeciality() {
        boolean result = doctorValidationService.validateSpeciality("NEUROLOGY");
        Assertions.assertTrue(result);
    }

    @Test
    void testValidateInvalidSpeciality() {
        boolean result = doctorValidationService.validateSpeciality("INVALID");
        Assertions.assertFalse(result);
    }
}