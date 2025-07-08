package com.example.basic.persons.application.services;

import com.example.basic.persons.application.ports.input.cases.PatientUseCase;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.repository.PatientRepositoryInterface;
import com.example.basic.persons.application.utils.PersonMapper;
import com.example.basic.persons.application.utils.builders.ResponseBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio que implementa los casos de uso relacionados con pacientes.
 * Se encarga de coordinar la lógica entre la capa de entrada (controladores)
 * y la capa de acceso a datos (repositorio).
 */
@Service
public class PatientService implements PatientUseCase {

    private final PatientRepositoryInterface patientRepository;
    private final PersonMapper mapper;

    /**
     * Constructor del servicio de pacientes.
     *
     * @param repositoryInterface Repositorio para acceder a los datos de pacientes.
     * @param personMapper Herramienta para convertir objetos entre capas.
     */
    public PatientService(PatientRepositoryInterface repositoryInterface, PersonMapper personMapper) {
        this.patientRepository = repositoryInterface;
        this.mapper = personMapper;
    }

    /**
     * Obtiene los datos de un paciente por su ID.
     */
    @Override
    public Mono<GenericResponse> getPatient(String patientId) {
        return ResponseBuilder.buildSingleResponse(
            patientRepository.findById(patientId),
            mapper::mapPatientToResponse
        );
    }

    /**
     * Busca pacientes por nombre y apellido.
     */
    @Override
    public Mono<GenericResponse> getPatientsByName(String name, String surname) {
        return ResponseBuilder.buildListResponse(
            patientRepository.findByFullName(name, surname),
            mapper::mapPatientsToResponse
        );
    }

    /**
     * Busca pacientes registrados dentro de un rango de fechas de nacimiento.
     */
    @Override
    public Mono<GenericResponse> getPatientsByDate(String initDate, String endDate) {
        return ResponseBuilder.buildListResponse(
            patientRepository.findByBirthDate(initDate, endDate),
            mapper::mapPatientsToResponse
        );
    }

    /**
     * Crea un nuevo paciente en el sistema.
     */
    @Override
    public Mono<GenericResponse> createPatient(PatientRequest patient) {
        return ResponseBuilder.buildSavedResponse(
            patientRepository.createPatient(patient),
            mapper::mapPatientToResponse
        );
    }
}
