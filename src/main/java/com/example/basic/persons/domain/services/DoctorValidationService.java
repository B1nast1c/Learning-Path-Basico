package com.example.basic.persons.domain.services;


import com.example.basic.exceptions.throwables.GenericExc;
import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.validations.FieldsValidations;
import org.springframework.stereotype.Service;

import static com.example.basic.persons.domain.validations.SpecialityValidations.validSpeciality;

@Service
public class DoctorValidationService {
    public void validate(Doctor doctor) {
        if (!validSpeciality(doctor.getSpecialization())) {
            throw new SpecialityExc("Invalid doctor speciality");
        }
        if (!FieldsValidations.validateInput(doctor.getPersonID())) {
            throw new GenericExc("Invalid doctor ID format");
        }
    }

    public boolean validateSpeciality(String speciality) {
        return validSpeciality(speciality);
    }
}

