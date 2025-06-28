package com.example.basic.persons.application.services;

import com.example.basic.persons.application.ports.input.cases.DoctorUseCase;
import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.repository.DoctorRepositoryInterface;
import com.example.basic.persons.application.utils.PersonMapper;
import com.example.basic.persons.application.utils.RequestBuilder;
import com.example.basic.persons.application.utils.ResponseBuilder;
import com.example.basic.persons.domain.models.Doctor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DoctorService implements DoctorUseCase {
    private final DoctorRepositoryInterface doctorRepository;
    private final PersonMapper mapper;

    public DoctorService(DoctorRepositoryInterface repositoryInterface, PersonMapper mapper) {
        this.doctorRepository = repositoryInterface;
        this.mapper = mapper;
    }

    @Override
    public Mono<GenericResponse> getDoctors() {
        return ResponseBuilder.buildListResponse(doctorRepository.findAll(), mapper::mapDoctorsToResponse);
    }

    @Override
    public Mono<GenericResponse> getDoctorsBySpeciality(String speciality) {
        return ResponseBuilder.buildListResponse(doctorRepository.findBySpeciality(speciality), mapper::mapDoctorsToResponse);
    }

    @Override
    public Mono<GenericResponse> getDoctor(String doctorID) {
        return ResponseBuilder.buildSingleResponse(doctorRepository.findById(doctorID), mapper::mapDoctorToResponse);
    }

    @Override
    public Mono<GenericResponse> getDoctorByFullName(String name, String surname) {
        return ResponseBuilder.buildListResponse(doctorRepository.findByFullName(name, surname), mapper::mapDoctorsToResponse);
    }

    @Override
    public Mono<GenericResponse> createDoctor(DoctorRequest request) {
        Doctor mapDoctor = RequestBuilder.buildDoctorRequest(request);
        return ResponseBuilder.buildSavedResponse(
                doctorRepository.createDoctor(mapDoctor), mapper::mapDoctorToResponse);
    }

    @Override
    public Mono<GenericResponse> removeDoctor(String doctorID) {
        return ResponseBuilder.buildSavedResponse(doctorRepository.deleteDoctor(doctorID), mapper::mapDoctorToResponse);
    }
}
