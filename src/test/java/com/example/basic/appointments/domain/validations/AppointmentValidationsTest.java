package com.example.basic.appointments.domain.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
class AppointmentValidationsTest {

    @Test
    void testValidatePersons(){
        boolean result = AppointmentValidations.validatePersons("doctorId", "patientId");
        Assertions.assertEquals(true, result);
    }

    @Test
    void testValidateSpeciality(){
        boolean result = AppointmentValidations.validateSpeciality(new com.example.basic.persons.domain.models.Doctor("personID", "personAppID", "personName", "personSurname", com.example.basic.persons.domain.models.AppRoles.PATIENT, true), "requestedSpeciality");
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme