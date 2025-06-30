package com.example.basic.persons.application.utils;

import com.example.basic.persons.application.ports.ouput.models.PersonsResponse;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Clase encargada de convertir objetos del dominio (Doctor, Patient)
 * en objetos de respuesta (PersonsResponse) que se envían al cliente.
 */
@Component
public class PersonMapper {

    private final ModelMapper mapper;

    /**
     * Constructor que recibe un ModelMapper configurado para personas.
     *
     * @param modelMapper Mapper usado para convertir objetos entre capas.
     */
    public PersonMapper(@Qualifier("personMapper") ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    /**
     * Convierte un paciente en una respuesta, incluyendo su fecha de nacimiento.
     *
     * @param patient Objeto Patient.
     * @return Objeto PersonsResponse con detalles del paciente.
     */
    public PersonsResponse mapPatientToResponse(Patient patient) {
        PersonsResponse mappedPerson = mapper.map(patient, PersonsResponse.class);
        mappedPerson.setPersonDetails("BIRTHDATE: " + patient.getBirthDate());
        return mappedPerson;
    }

    /**
     * Convierte un doctor en una respuesta, incluyendo su especialidad.
     *
     * @param doctor Objeto Doctor.
     * @return Objeto PersonsResponse con detalles del doctor.
     */
    public PersonsResponse mapDoctorToResponse(Doctor doctor) {
        PersonsResponse mappedPerson = mapper.map(doctor, PersonsResponse.class);
        mappedPerson.setPersonDetails("SPECIALITY: " + doctor.getSpecialization());
        return mappedPerson;
    }

    /**
     * Convierte una lista de pacientes en una lista de respuestas.
     *
     * @param patients Lista de objetos Patient.
     * @return Lista de PersonsResponse con detalles de cada paciente.
     */
    public List<PersonsResponse> mapPatientsToResponse(List<Patient> patients) {
        return patients.stream()
            .map(patient -> {
                PersonsResponse mappedPerson = mapper.map(patient, PersonsResponse.class);
                mappedPerson.setPersonDetails("BIRTHDATE: " + patient.getBirthDate());
                return mappedPerson;
            })
            .toList();
    }

    /**
     * Convierte una lista de doctores en una lista de respuestas.
     *
     * @param persons Lista de objetos Doctor.
     * @return Lista de PersonsResponse con detalles de cada doctor.
     */
    public List<PersonsResponse> mapDoctorsToResponse(List<Doctor> persons) {
        return persons.stream()
            .map(doctor -> {
                PersonsResponse mappedPerson = mapper.map(doctor, PersonsResponse.class);
                mappedPerson.setPersonDetails("SPECIALITY: " + doctor.getSpecialization());
                return mappedPerson;
            })
            .toList();
    }
}
