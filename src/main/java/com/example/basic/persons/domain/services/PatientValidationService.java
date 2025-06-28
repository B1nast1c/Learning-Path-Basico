package com.example.basic.persons.domain.services;

import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.domain.models.Patient;
import com.example.basic.persons.domain.validations.DateValidations;
import com.example.basic.persons.domain.validations.FieldsValidations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PatientValidationService {
    public void validate(Patient patient) {
        if (!FieldsValidations.validateInput(patient.getPersonID())) {
            throw new SpecialityExc("Invalid patient ID format");
        }
        if (!DateValidations.validateBirthDate(patient.getBirthDate())) {
            throw new SpecialityExc("Invalid birth date");
        }
    }

    public boolean validateDateRange(LocalDate start, LocalDate end) {
        return DateValidations.validateDateRanges(start, end);
    }
}
