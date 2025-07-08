package com.example.basic.persons.application.ports.input.cases;

import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import reactor.core.publisher.Mono;

/**
 * Interfaz que define las operaciones disponibles para trabajar con pacientes.
 */
public interface PatientUseCase {

    /**
     * Obtiene los datos de un paciente específico por su ID.
     *
     * @param patientId ID del paciente.
     * @return Respuesta con los datos del paciente.
     */
    Mono<GenericResponse> getPatient(String patientId);

    /**
     * Busca pacientes por nombre y apellido.
     *
     * @param name Nombre del paciente.
     * @param surname Apellido del paciente.
     * @return Respuesta con la lista de pacientes que coinciden.
     */
    Mono<GenericResponse> getPatientsByName(String name, String surname);

    /**
     * Busca pacientes registrados dentro de un rango de fechas.
     *
     * @param initDate Fecha de inicio (formato: YYYY-MM-DD).
     * @param endDate Fecha de fin (formato: YYYY-MM-DD).
     * @return Respuesta con la lista de pacientes registrados en ese rango.
     */
    Mono<GenericResponse> getPatientsByDate(String initDate, String endDate);

    /**
     * Crea un nuevo paciente en el sistema.
     *
     * @param patient Datos del paciente a registrar.
     * @return Respuesta indicando si la operación fue exitosa.
     */
    Mono<GenericResponse> createPatient(PatientRequest patient);
}
