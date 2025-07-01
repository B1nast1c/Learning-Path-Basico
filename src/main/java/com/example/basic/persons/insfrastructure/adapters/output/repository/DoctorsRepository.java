package com.example.basic.persons.insfrastructure.adapters.output.repository;

import com.example.basic.exceptions.throwables.DuplicateExc;
import com.example.basic.exceptions.throwables.NotFoundExc;
import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.application.ports.ouput.repository.DoctorRepositoryInterface;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Person;
import com.example.basic.persons.domain.services.DoctorValidationService;
import com.example.basic.persons.insfrastructure.repositories.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del repositorio de salida para operaciones con doctores.
 */
@Slf4j
@Component
public class DoctorsRepository implements DoctorRepositoryInterface {

    private final DoctorRepository doctorRepository;
    private final DoctorValidationService doctorValidationService;

    /**
     * Constructor que inyecta el repositorio y el servicio de validación.
     *
     * @param repository Repositorio de acceso a datos de doctores.
     * @param validationService Servicio de validación de doctores.
     */
    public DoctorsRepository(
        DoctorRepository repository,
        DoctorValidationService validationService) {
        this.doctorRepository = repository;
        this.doctorValidationService = validationService;
    }

    /**
     * Obtiene todos los doctores registrados.
     *
     * @return Flujo reactivo con todos los doctores.
     */
    @Override
    public Flux<Doctor> findAll() {
        log.info("doctor.adapters.output.repository::findAll()");
        return doctorRepository.findAll()
            .filter(Person::isActive);
    }

    /**
     * Busca doctores por especialidad.
     *
     * @param speciality Especialidad médica.
     * @return Flujo reactivo con los doctores que coinciden con la especialidad.
     * @throws SpecialityExc Si la especialidad no es válida.
     * @throws NotFoundExc Si no se encuentran doctores.
     */
    @Override
    public Flux<Doctor> findBySpeciality(String speciality) {
        log.info("doctor.adapters.output.repository::findBySpeciality()");
        return Mono.fromCallable(() -> doctorValidationService.validateSpeciality(speciality))
            .flatMapMany(isValid -> {
                if (isValid.equals(Boolean.TRUE)) {
                    return doctorRepository.findBySpecializationIgnoreCaseOrderByPersonNameAsc(speciality)
                        .filter(Person::isActive);
                }
                return Flux.error(new SpecialityExc("Invalid speciality"));
            })
            .switchIfEmpty(Mono.error(new NotFoundExc("There are no doctors available")));
    }

    /**
     * Busca un doctor por su ID.
     *
     * @param doctorId ID del doctor.
     * @return Mono con el doctor encontrado.
     * @throws NotFoundExc Si no se encuentra el doctor.
     */
    @Override
    public Mono<Doctor> findById(String doctorId) {
        log.info("doctor.adapters.output.repository::findById()");
        return doctorRepository.findByPersonID(doctorId)
            .filter(Person::isActive)
            .switchIfEmpty(Mono.error(new NotFoundExc("Doctor not found")));
    }

    /**
     * Busca doctores por nombre y apellido.
     *
     * @param name Nombre del doctor.
     * @param surname Apellido del doctor.
     * @return Flujo reactivo con los doctores encontrados.
     * @throws NotFoundExc Si no se encuentran coincidencias.
     */
    @Override
    public Flux<Doctor> findByFullName(String name, String surname) {
        log.info("doctor.adapters.output.repository::findByFullName()");
        return doctorRepository.findByPersonNameIgnoreCaseAndPersonSurnameIgnoreCase(name, surname)
            .filter(Person::isActive)
            .switchIfEmpty(Mono.error(new NotFoundExc("Not enough data to retrieve")));
    }

    /**
     * Crea un nuevo doctor si no existe previamente.
     *
     * @param doctor Objeto Doctor a crear.
     * @return Mono con el doctor creado.
     * @throws DuplicateExc Si el doctor ya existe.
     * @throws Exception Si ocurre un error de validación.
     */
    @Override
    public Mono<Doctor> createDoctor(Doctor doctor) {
        log.info("doctor.adapters.output.repository::createDoctor()");
        return doctorRepository.existsByPersonID(doctor.getPersonID())
            .filter(exists -> !exists)
            .flatMap(found -> {
                try {
                    doctorValidationService.validate(doctor);
                    return doctorRepository.save(doctor)
                        .doOnSuccess(saved -> log.info("Entity saved successfully"));
                } catch (Exception e) {
                    return Mono.error(e);
                }
            })
            .switchIfEmpty(Mono.error(new DuplicateExc("Entity already in the database")));
    }

    /**
     * Elimina (desactiva) un doctor por su ID.
     *
     * @param doctorId ID del doctor a eliminar.
     * @return Mono con el doctor modificado.
     * @throws NotFoundExc Si no se encuentra el doctor activo.
     */
    @Override
    public Mono<Doctor> deleteDoctor(String doctorId) {
        log.info("doctor.adapters.output.repository::deleteDoctor()");
        return doctorRepository.findByPersonID(doctorId)
            .filter(Person::isActive)
            .flatMap(found -> {
                found.setActive(Boolean.FALSE);
                return doctorRepository.save(found)
                    .doOnSuccess(deleted -> log.info("Entity deleted successfully"));
            })
            .switchIfEmpty(Mono.error(new NotFoundExc("Cannot find an entity to modify")));
    }
}
