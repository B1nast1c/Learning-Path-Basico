package com.example.basic.persons.application.ports.input.cases;

import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import reactor.core.publisher.Mono;

/**
 * Interfaz que define las operaciones disponibles para trabajar con doctores.
 */
public interface DoctorUseCase {

    /**
     * Obtiene la lista de todos los doctores registrados.
     *
     * @return Respuesta con la lista de doctores.
     */
    Mono<GenericResponse> getDoctors();

    /**
     * Obtiene doctores filtrados por especialidad.
     *
     * @param speciality Especialidad médica.
     * @return Respuesta con los doctores que tienen esa especialidad.
     */
    Mono<GenericResponse> getDoctorsBySpeciality(String speciality);

    /**
     * Obtiene los datos de un doctor específico por su ID.
     *
     * @param doctorID ID del doctor.
     * @return Respuesta con los datos del doctor.
     */
    Mono<GenericResponse> getDoctor(String doctorID);

    /**
     * Busca un doctor por su nombre y apellido.
     *
     * @param name Nombre del doctor.
     * @param surname Apellido del doctor.
     * @return Respuesta con los datos del doctor si existe.
     */
    Mono<GenericResponse> getDoctorByFullName(String name, String surname);

    /**
     * Crea un nuevo doctor en el sistema.
     *
     * @param request Datos del doctor a registrar.
     * @return Respuesta indicando si la operación fue exitosa.
     */
    Mono<GenericResponse> createDoctor(DoctorRequest request);

    /**
     * Elimina un doctor del sistema por su ID.
     *
     * @param doctorID ID del doctor a eliminar.
     * @return Respuesta indicando si la eliminación fue exitosa o no.
     */
    Mono<GenericResponse> removeDoctor(String doctorID);
}
