package com.example.basic.persons.application.utils.builders;

import com.example.basic.persons.application.ports.input.models.DoctorRequest;
import com.example.basic.persons.application.ports.input.models.PatientRequest;
import com.example.basic.persons.domain.models.AppRoles;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;

/**
 * Clase utilitaria para construir objetos de tipo Doctor y Patient
 * a partir de sus respectivos modelos de solicitud.
 */
public class RequestBuilder {
    // Reutilizado para construir el personAppID
    private static final StringBuilder builder = new StringBuilder();

    /**
     * Construye un objeto Doctor a partir de los datos recibidos en la solicitud.
     *
     * @param request Datos del doctor.
     * @return Objeto Doctor listo para ser guardado.
     */
    public static Doctor buildDoctorRequest(DoctorRequest request) {
        builder.setLength(0); // Limpia el builder antes de usarlo
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

    /**
     * Construye un objeto Patient a partir de los datos recibidos en la solicitud.
     *
     * @param request Datos del paciente.
     * @return Objeto Patient listo para ser guardado.
     */
    public static Patient buildPatientRequest(PatientRequest request) {
        builder.setLength(0); // Limpia el builder antes de usarlo
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
