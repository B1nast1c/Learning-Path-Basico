package com.example.basic.persons.insfrastructure.adapters.input.controllers;

import com.example.basic.persons.application.ports.input.cases.PatientUseCase;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientUseCase patientUseCase;

    public PatientController(PatientUseCase cases) {
        this.patientUseCase = cases;
    }

    @GetMapping("/id/{patientId}")
    public Mono<GenericResponse> getPatientById(@PathVariable String patientId) {
        return patientUseCase.getPatient(patientId);
    }

    @GetMapping("/fullName/{name}/{surname}")
    public Mono<GenericResponse> getPatientsByFullName(@PathVariable String name, @PathVariable String surname) {
        return patientUseCase.getPatientsByName(name, surname);
    }

    @GetMapping("/birthDate")
    public Mono<GenericResponse> getPatientsByBirthDate(@RequestParam String initDate, @RequestParam String endDate) {
        return patientUseCase.getPatientsByDate(initDate, endDate);
    }

    @PostMapping("/create")
    public Mono<GenericResponse> createPatient(@RequestBody PatientRequest request) {
        return patientUseCase.createPatient(request);
    }
}
