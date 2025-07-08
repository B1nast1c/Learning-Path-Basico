package com.example.basic.persons.domain.services;

import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.domain.models.Patient;
import com.example.basic.persons.domain.validations.DateValidations;
import com.example.basic.persons.domain.validations.FieldsValidations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Servicio encargado de validar los datos de un paciente antes de ser procesados o almacenados.
 */
@Service
public class PatientValidationService {

    /**
     * Valida los campos principales de un paciente.
     *
     * @param patient Objeto Patient a validar.
     * @throws SpecialityExc Si el ID del paciente es inválido o la fecha de nacimiento no es válida.
     */
    public void validate(Patient patient) {
        if (!FieldsValidations.validateInput(patient.getPersonID())) {
            throw new SpecialityExc("Invalid patient ID format");
        }
        if (!DateValidations.validateBirthDate(patient.getBirthDate())) {
            throw new SpecialityExc("Invalid birth date");
        }
    }

    /**
     * Verifica si un rango de fechas es válido.
     *
     * @param start Fecha de inicio.
     * @param end Fecha de fin.
     * @return true si el rango de fechas es válido, false en caso contrario.
     */
    public boolean validateDateRange(LocalDate start, LocalDate end) {
        return DateValidations.validateDateRanges(start, end);
    }
}
