package com.example.basic.persons.domain.validations;

public class FieldsValidations {
    public static boolean validateInput(String personID) {
        return personID.matches("^\\d{8}$");
    }
}
