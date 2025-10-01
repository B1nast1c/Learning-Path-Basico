package com.example.basic.persons.insfrastructure.repositories;

import com.example.basic.persons.domain.models.Patient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * Repositorio reactivo para acceder a datos de pacientes en MongoDB.
 */
@Repository
public interface PatientRepository extends ReactiveMongoRepository<Patient, String> {

    /**
     * Busca un paciente por su ID personal.
     *
     * @param personID ID del paciente.
     * @return Mono con el paciente encontrado, si existe.
     */
    Mono<Patient> findByPersonID(String personID);

    /**
     * Busca pacientes por nombre y apellido, ignorando mayúsculas y minúsculas.
     *
     * @param personName    Nombre del paciente.
     * @param personSurname Apellido del paciente.
     * @return Flujo reactivo con los pacientes encontrados.
     */
    Flux<Patient> findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(String personName, String personSurname);

    /**
     * Busca pacientes cuya fecha de nacimiento esté dentro del rango especificado.
     *
     * @param birthDateStart Fecha de inicio del rango.
     * @param birthDateEnd   Fecha de fin del rango.
     * @return Flujo reactivo con los pacientes encontrados.
     */
    Flux<Patient> findByBirthDateBetween(LocalDate birthDateStart, LocalDate birthDateEnd);

    /**
     * Verifica si existe un paciente con el ID proporcionado.
     *
     * @param personID ID del paciente.
     * @return Mono con valor booleano indicando si existe.
     */
    Mono<Boolean> existsByPersonID(String personID);
}
