package com.example.basic.appointments.domain.validations;

import com.example.basic.persons.domain.models.Doctor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppointmentValidations {
    public static boolean validatePersons(String doctorId, String patientId) {
        return !doctorId.equals(patientId);
    }

    public static boolean validateSpeciality(Doctor requestedDoctor, String requestedSpeciality) {
        log.info("appointments.domain.validations::validateSpeciality");
        return requestedDoctor.getSpecialization().equalsIgnoreCase(requestedSpeciality);
    }
}
