package com.example.basic.persons.domain.validations;

import com.example.basic.persons.domain.models.Specializations;
import org.springframework.util.ObjectUtils;

public class SpecialityValidations {
    public static boolean validSpeciality(String speciality) {
        return ObjectUtils.containsConstant(Specializations.values(), speciality, false);
    }
}
