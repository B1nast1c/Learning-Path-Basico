package com.example.basic.persons.domain.validations;

/**
 * Clase utilitaria para validar campos de texto relacionados con personas.
 */
public class FieldsValidations {

    /**
     * Valida el formato del ID de una persona.
     * El ID debe contener exactamente 8 dígitos numéricos.
     *
     * @param personID Cadena que representa el ID de la persona.
     * @return true si el ID tiene un formato válido, false en caso contrario.
     */
    public static boolean validateInput(String personID) {
        return personID.matches("^\\d{8}$");
    }
}
