package com.example.basic.persons.application.services;

import com.example.basic.persons.application.ports.input.cases.PatientUseCase;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.repository.PatientRepositoryInterface;
import com.example.basic.persons.application.utils.*;
import com.example.basic.persons.domain.models.Patient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PatientService implements PatientUseCase {
    private final PatientRepositoryInterface patientRepository;
    private final PersonMapper mapper;

    public PatientService(PatientRepositoryInterface repositoryInterface, PersonMapper personMapper) {
        this.patientRepository = repositoryInterface;
        this.mapper = personMapper;
    }

    @Override
    public Mono<GenericResponse> getPatient(String patientId) {
        return ResponseBuilder.buildSingleResponse(patientRepository.findById(patientId), mapper::mapPatientToResponse);
    }

    @Override
    public Mono<GenericResponse> getPatientsByName(String name, String surname) {
        return ResponseBuilder.buildListResponse(patientRepository.findByFullName(name, surname), mapper::mapPatientsToResponse);
    }

    @Override
    public Mono<GenericResponse> getPatientsByDate(String initDate, String endDate) {
        return ResponseBuilder
            .buildListResponse(patientRepository.findByBirthDate(initDate, endDate), mapper::mapPatientsToResponse);
    }

    @Override public Mono<GenericResponse> createPatient(PatientRequest patient) {
        Patient mapPatient = RequestBuilder.buildPatientRequest(patient);
        return ResponseBuilder.buildSavedResponse(patientRepository.createPatient(mapPatient), mapper::mapPatientToResponse);
    }

    @Override
    public Mono<GenericResponse> deletePatient(String patientId) {
        return ResponseBuilder.buildSavedResponse(patientRepository.deletePatient(patientId), mapper::mapPatientToResponse);
    }
}
