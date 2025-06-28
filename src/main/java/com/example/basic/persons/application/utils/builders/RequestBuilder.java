package com.example.basic.persons.application.utils.builders;

import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;

public class RequestBuilder {
    private static final StringBuilder builder = new StringBuilder();

    public static Doctor buildDoctorRequest(DoctorRequest request) {
        builder.setLength(0);
        return Doctor.builder()
                .personID(request.getPersonID())
                .personAppID(builder
                    .append(AppRoles.DOCTOR.name())
                    .append("-")
                    .append(request.getPersonID())
                    .toString())
                .personName(request.getPersonName())
                .personSurname(request.getPersonSurname())
                .personRole(AppRoles.DOCTOR)
                .specialization(request.getSpecialization().toUpperCase())
                .isActive(Boolean.TRUE)
                .build();
    }

    public static Patient buildPatientRequest(PatientRequest request) {
        builder.setLength(0);
        return Patient.builder()
            .personID(request.getPersonID())
            .personAppID(builder
                .append(AppRoles.PATIENT.name())
                .append("-")
                .append(request.getPersonID())
                .toString())
            .personName(request.getPersonName())
            .personSurname(request.getPersonSurname())
            .personRole(AppRoles.PATIENT)
            .birthDate(DateParser.parseDate(request.getBirthDate()))
            .isActive(Boolean.TRUE)
            .build();
    }
}
