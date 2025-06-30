package com.example.basic.persons.insfrastructure.adapters.input.controllers;

import com.example.basic.persons.application.ports.input.cases.DoctorUseCase;
import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestionar operaciones relacionadas con doctores.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorUseCase doctorUseCase;

    /**
     * Constructor que inyecta el caso de uso para doctores.
     *
     * @param cases Implementación de la lógica de negocio para doctores.
     */
    public DoctorController(DoctorUseCase cases) {
        this.doctorUseCase = cases;
    }

    /**
     * Obtiene todos los doctores registrados.
     *
     * @return Mono con la respuesta genérica que contiene la lista de doctores.
     */
    @GetMapping
    public Mono<GenericResponse> getDoctors() {
        return doctorUseCase.getDoctors();
    }

    /**
     * Obtiene doctores filtrados por especialidad.
     *
     * @param speciality Especialidad médica.
     * @return Mono con la respuesta genérica que contiene los doctores filtrados.
     */
    @GetMapping("/speciality/{speciality}")
    public Mono<GenericResponse> getDoctorsBySpeciality(@PathVariable String speciality) {
        return doctorUseCase.getDoctorsBySpeciality(speciality);
    }

    /**
     * Obtiene un doctor por su ID.
     *
     * @param doctorId ID del doctor.
     * @return Mono con la respuesta genérica que contiene los datos del doctor.
     */
    @GetMapping("/id/{doctorId}")
    public Mono<GenericResponse> getDoctorsById(@PathVariable String doctorId) {
        return doctorUseCase.getDoctor(doctorId);
    }

    /**
     * Obtiene doctores por nombre y apellido.
     *
     * @param name Nombre del doctor.
     * @param surname Apellido del doctor.
     * @return Mono con la respuesta genérica que contiene los doctores encontrados.
     */
    @GetMapping("/fullName/{name}/{surname}")
    public Mono<GenericResponse> getDoctorsByFullName(@PathVariable String name, @PathVariable String surname) {
        return doctorUseCase.getDoctorByFullName(name, surname);
    }

    /**
     * Crea un nuevo doctor con los datos proporcionados.
     *
     * @param request Objeto con los datos del doctor.
     * @return Mono con la respuesta genérica del resultado de la operación.
     */
    @PostMapping("/create")
    public Mono<GenericResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return doctorUseCase.createDoctor(request);
    }

    /**
     * Elimina (o desactiva) un doctor por su ID.
     *
     * @param doctorId ID del doctor a eliminar.
     * @return Mono con la respuesta genérica del resultado de la operación.
     */
    @PutMapping("/remove/{doctorId}")
    public Mono<GenericResponse> removeDoctor(@PathVariable String doctorId) {
        return doctorUseCase.removeDoctor(doctorId);
    }
}
