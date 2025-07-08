package com.example.basic.persons.domain.validations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldsValidationsTest {

    @Test
    void testValidInput() {
        assertTrue(FieldsValidations.validateInput("12345678"));
    }

    @Test
    void testInvalidInput() {
        assertFalse(FieldsValidations.validateInput("123456789"));
    }

    @Test
    void testContainsLetters() {
        assertFalse(FieldsValidations.validateInput("1234ABCD"));
    }

    @Test
    void testEmptyString() {
        assertFalse(FieldsValidations.validateInput(""));
    }
}
