package com.example.basic.persons.application.utils;

import com.example.basic.persons.application.ports.ouput.models.PersonsResponse;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonMapper {
    private final ModelMapper mapper;

    public PersonMapper(@Qualifier("personMapper") ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    public PersonsResponse mapPatientToResponse(Patient patient) {
        PersonsResponse mappedPerson =  mapper.map(patient, PersonsResponse.class);
        mappedPerson.setPersonDetails("BIRTHDATE: " + patient.getBirthDate());
        return mappedPerson;
    }

    public PersonsResponse mapDoctorToResponse(Doctor doctor) {
        PersonsResponse mappedPerson =  mapper.map(doctor, PersonsResponse.class);
        mappedPerson.setPersonDetails("SPECIALITY: " + doctor.getSpecialization());
        return mappedPerson;
    }

    public List<PersonsResponse> mapPatientsToResponse(List<Patient> patients) {
        return patients
            .stream()
            .map((Patient patient) -> {
                PersonsResponse mappedPerson = mapper.map(patient, PersonsResponse.class);
                mappedPerson.setPersonDetails("BIRTHDATE: " + patient.getBirthDate());
                return mappedPerson;
            })
            .toList();
    }

    public List<PersonsResponse> mapDoctorsToResponse(List<Doctor> persons) {
        return persons
            .stream()
            .map((Doctor doctor) -> {
                PersonsResponse mappedPerson = mapper.map(doctor, PersonsResponse.class);
                mappedPerson.setPersonDetails("SPECIALITY: " + doctor.getSpecialization());
                return mappedPerson;
            })
            .toList();
    }
}
