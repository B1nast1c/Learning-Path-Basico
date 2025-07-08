package com.example.basic.appointments.domain.validations;

import com.example.basic.persons.domain.models.Doctor;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase de utilidades para validar reglas relacionadas con las citas médicas.
 * Contiene métodos estáticos que ayudan a verificar si los datos de una solicitud de cita son válidos.
 */
@Slf4j
public class AppointmentValidations {

    /**
     * Verifica que el doctor y el paciente no sean la misma persona.
     *
     * @param doctorId ID del doctor.
     * @param patientId ID del paciente.
     * @return true si son diferentes, false si son iguales.
     */
    public static boolean validatePersons(String doctorId, String patientId) {
        return !doctorId.equals(patientId);
    }

    /**
     * Verifica que la especialidad del doctor coincida con la solicitada.
     *
     * @param requestedDoctor Objeto Doctor con su especialidad.
     * @param requestedSpeciality Especialidad solicitada en la cita.
     * @return true si coinciden, false si no.
     */
    public static boolean validateSpeciality(Doctor requestedDoctor, String requestedSpeciality) {
        log.info("appointments.domain.validations::validateSpeciality");
        return requestedDoctor.getSpecialization().equalsIgnoreCase(requestedSpeciality);
    }
}
