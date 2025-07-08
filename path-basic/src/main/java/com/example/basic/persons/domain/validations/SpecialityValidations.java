package com.example.basic.persons.domain.validations;

import com.example.basic.persons.domain.models.Specializations;
import org.springframework.util.ObjectUtils;

/**
 * Clase utilitaria para validar especialidades médicas.
 */
public class SpecialityValidations {

    /**
     * Verifica si una especialidad médica es válida.
     *
     * @param speciality Cadena que representa la especialidad médica.
     * @return true si la especialidad es válida, false en caso contrario.
     */
    public static boolean validSpeciality(String speciality) {
        return ObjectUtils.containsConstant(Specializations.values(), speciality, false);
    }
}
