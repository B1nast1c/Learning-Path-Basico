package com.example.basic.persons.application.services;

import com.example.basic.persons.application.ports.input.cases.DoctorUseCase;
import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.repository.DoctorRepositoryInterface;
import com.example.basic.persons.application.utils.PersonMapper;
import com.example.basic.persons.application.utils.builders.RequestBuilder;
import com.example.basic.persons.application.utils.builders.ResponseBuilder;
import com.example.basic.persons.domain.models.Doctor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio que implementa los casos de uso relacionados con doctores.
 * Se encarga de coordinar la lógica entre la capa de entrada (controladores)
 * y la capa de acceso a datos (repositorio).
 */
@Slf4j
@Service
public class DoctorService implements DoctorUseCase {

    private final DoctorRepositoryInterface doctorRepository;
    private final PersonMapper mapper;

    /**
     * Constructor del servicio de doctores.
     *
     * @param repositoryInterface Repositorio para acceder a los datos de doctores.
     * @param mapper Herramienta para convertir objetos entre capas.
     */
    public DoctorService(DoctorRepositoryInterface repositoryInterface, PersonMapper mapper) {
        this.doctorRepository = repositoryInterface;
        this.mapper = mapper;
    }

    /**
     * Obtiene todos los doctores registrados.
     */
    @Override
    public Mono<GenericResponse> getDoctors() {
        return ResponseBuilder.buildListResponse(
            doctorRepository.findAll(),
            mapper::mapDoctorsToResponse
        );
    }

    /**
     * Obtiene doctores filtrados por especialidad.
     */
    @Override
    public Mono<GenericResponse> getDoctorsBySpeciality(String speciality) {
        return ResponseBuilder.buildListResponse(
            doctorRepository.findBySpeciality(speciality),
            mapper::mapDoctorsToResponse
        );
    }

    /**
     * Obtiene un doctor por su ID.
     */
    @Override
    public Mono<GenericResponse> getDoctor(String doctorID) {
        return ResponseBuilder.buildSingleResponse(
            doctorRepository.findById(doctorID),
            mapper::mapDoctorToResponse
        );
    }

    /**
     * Busca doctores por nombre y apellido.
     */
    @Override
    public Mono<GenericResponse> getDoctorByFullName(String name, String surname) {
        return ResponseBuilder.buildListResponse(
            doctorRepository.findByFullName(name, surname),
            mapper::mapDoctorsToResponse
        );
    }

    /**
     * Crea un nuevo doctor en el sistema.
     */
    @Override
    public Mono<GenericResponse> createDoctor(DoctorRequest request) {
        Doctor mapDoctor = RequestBuilder.buildDoctorRequest(request);
        return ResponseBuilder.buildSavedResponse(
            doctorRepository.createDoctor(mapDoctor),
            mapper::mapDoctorToResponse
        );
    }

    /**
     * Elimina un doctor del sistema por su ID.
     */
    @Override
    public Mono<GenericResponse> removeDoctor(String doctorID) {
        return ResponseBuilder.buildSavedResponse(
            doctorRepository.deleteDoctor(doctorID),
            mapper::mapDoctorToResponse
        );
    }
}
