package com.example.basic.persons.domain.validations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialityValidationsTest {

    @Test
    void testValidSpeciality() {
        assertTrue(SpecialityValidations.validSpeciality("CARDIOLOGY"));
        assertTrue(SpecialityValidations.validSpeciality("cardiology"));
    }

    @Test
    void testEmptySpeciality() {
        assertFalse(SpecialityValidations.validSpeciality(""));
    }
}
