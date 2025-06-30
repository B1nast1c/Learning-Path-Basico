package com.example.basic.persons.insfrastructure.repositories;

import com.example.basic.persons.domain.models.Doctor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repositorio reactivo para acceder a datos de doctores en MongoDB.
 */
@Repository
public interface DoctorRepository extends ReactiveMongoRepository<Doctor, String> {

    /**
     * Busca doctores por especialidad, ignorando mayúsculas y minúsculas, y ordenados por nombre.
     *
     * @param specialization Especialidad médica.
     * @return Flujo reactivo con los doctores encontrados.
     */
    Flux<Doctor> findBySpecializationIgnoreCaseOrderByPersonNameAsc(String specialization);

    /**
     * Busca un doctor por su ID personal.
     *
     * @param personID ID del doctor.
     * @return Mono con el doctor encontrado, si existe.
     */
    Mono<Doctor> findByPersonID(String personID);

    /**
     * Verifica si existe un doctor con el ID proporcionado.
     *
     * @param personID ID del doctor.
     * @return Mono con valor booleano indicando si existe.
     */
    Mono<Boolean> existsByPersonID(String personID);

    /**
     * Busca doctores por nombre y apellido, ignorando mayúsculas y minúsculas.
     *
     * @param personName Nombre del doctor.
     * @param personSurname Apellido del doctor.
     * @return Flujo reactivo con los doctores encontrados.
     */
    Flux<Doctor> findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(String personName, String personSurname);
}
