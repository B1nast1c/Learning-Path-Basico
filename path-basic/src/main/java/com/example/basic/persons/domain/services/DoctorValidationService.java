package com.example.basic.persons.domain.services;

import com.example.basic.exceptions.throwables.GenericExc;
import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.validations.FieldsValidations;
import org.springframework.stereotype.Service;

import static com.example.basic.persons.domain.validations.SpecialityValidations.validSpeciality;

/**
 * Servicio encargado de validar los datos de un doctor antes de ser procesados o almacenados.
 */
@Service
public class DoctorValidationService {

    /**
     * Valida los campos principales de un doctor.
     *
     * @param doctor Objeto Doctor a validar.
     * @throws SpecialityExc Si la especialidad del doctor no es válida.
     * @throws GenericExc Si el ID del doctor tiene un formato incorrecto.
     */
    public void validate(Doctor doctor) {
        if (!validSpeciality(doctor.getSpecialization())) {
            throw new SpecialityExc(
                    "Invalid doctor speciality, must be one of: CARDIOLOGY - NEUROLOGY - PEDIATRICS - OBSTETRICS - EMERGENCY"
            );
        }
        if (!FieldsValidations.validateInput(doctor.getPersonID())) {
            throw new GenericExc("Invalid doctor ID format");
        }
    }

    /**
     * Verifica si una especialidad médica es válida.
     *
     * @param speciality Cadena con el nombre de la especialidad.
     * @return true si la especialidad es válida, false en caso contrario.
     */
    public boolean validateSpeciality(String speciality) {
        return validSpeciality(speciality);
    }
}
