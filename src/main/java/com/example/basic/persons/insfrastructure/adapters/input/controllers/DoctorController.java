package com.example.basic.persons.insfrastructure.adapters.input.controllers;


import com.example.basic.persons.application.ports.input.cases.DoctorUseCase;
import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorUseCase doctorUseCase;

    public DoctorController(DoctorUseCase cases) {
        this.doctorUseCase = cases;
    }

    @GetMapping
    public Mono<GenericResponse> getDoctors() {
        return doctorUseCase.getDoctors();
    }

    @GetMapping("/speciality/{speciality}")
    public Mono<GenericResponse> getDoctorsBySpeciality(@PathVariable String speciality) {
        return doctorUseCase.getDoctorsBySpeciality(speciality);
    }

    @GetMapping("/id/{doctorId}")
    public Mono<GenericResponse> getDoctorsById(@PathVariable String doctorId) {
        return doctorUseCase.getDoctor(doctorId);
    }

    @GetMapping("/fullName/{name}/{surname}")
    public Mono<GenericResponse> getDoctorsByFullName(@PathVariable String name, @PathVariable String surname) {
        return doctorUseCase.getDoctorByFullName(name, surname);
    }

    @PostMapping("/create")
    public Mono<GenericResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return doctorUseCase.createDoctor(request);
    }

    @PutMapping("/remove/{doctorId}")
    public Mono<GenericResponse> removeDoctor(@PathVariable String doctorId) {
        return doctorUseCase.removeDoctor(doctorId);
    }
}
