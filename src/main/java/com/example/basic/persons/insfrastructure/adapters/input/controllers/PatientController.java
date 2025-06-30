package com.example.basic.persons.insfrastructure.adapters.input.controllers;

import com.example.basic.persons.application.ports.input.cases.PatientUseCase;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestionar operaciones relacionadas con pacientes.
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientUseCase patientUseCase;

    /**
     * Constructor que inyecta el caso de uso para pacientes.
     *
     * @param cases Implementación de la lógica de negocio para pacientes.
     */
    public PatientController(PatientUseCase cases) {
        this.patientUseCase = cases;
    }

    /**
     * Obtiene un paciente por su ID.
     *
     * @param patientId ID del paciente.
     * @return Mono con la respuesta genérica que contiene los datos del paciente.
     */
    @GetMapping("/id/{patientId}")
    public Mono<GenericResponse> getPatientById(@PathVariable String patientId) {
        return patientUseCase.getPatient(patientId);
    }

    /**
     * Obtiene pacientes por nombre y apellido.
     *
     * @param name Nombre del paciente.
     * @param surname Apellido del paciente.
     * @return Mono con la respuesta genérica que contiene los pacientes encontrados.
     */
    @GetMapping("/fullName/{name}/{surname}")
    public Mono<GenericResponse> getPatientsByFullName(@PathVariable String name, @PathVariable String surname) {
        return patientUseCase.getPatientsByName(name, surname);
    }

    /**
     * Obtiene pacientes dentro de un rango de fechas de nacimiento.
     *
     * @param initDate Fecha de inicio del rango.
     * @param endDate Fecha de fin del rango.
     * @return Mono con la respuesta genérica que contiene los pacientes encontrados.
     */
    @GetMapping("/birthDate")
    public Mono<GenericResponse> getPatientsByBirthDate(@RequestParam String initDate, @RequestParam String endDate) {
        return patientUseCase.getPatientsByDate(initDate, endDate);
    }

    /**
     * Crea un nuevo paciente con los datos proporcionados.
     *
     * @param request Objeto con los datos del paciente.
     * @return Mono con la respuesta genérica del resultado de la operación.
     */
    @PostMapping("/create")
    public Mono<GenericResponse> createPatient(@RequestBody PatientRequest request) {
        return patientUseCase.createPatient(request);
    }
}
