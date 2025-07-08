package com.example.basic.appointments.application.utils.builders;

import com.example.basic.appointments.application.ports.input.models.RequestModel;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.exceptions.throwables.SpecialityExc;
import com.example.basic.persons.domain.models.Specializations;

/**
 * Constructor de citas médicas generadas por una solicitud.
 */
public class RequestBuilder {
    public static AppointmentRequest buildAppointmentRequest(RequestModel request) {
        Specializations specialization;
        if (request.getRequestSpeciality() != null && !request.getRequestSpeciality().isBlank()) {
            try {
                specialization = Specializations.valueOf(request.getRequestSpeciality().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new SpecialityExc("Invalid speciality: " + request.getRequestSpeciality());
            }
        } else {
            throw new SpecialityExc("Empty/Null speciality");
        }

        String generatedId = IndexBuilder.generateRequestId(request.getRequestDate(), request.getPatientIdRequest(), request.getDoctorIdRequest());

        return AppointmentRequest.builder()
            .requestId(generatedId)
            .patientIdRequest(request.getPatientIdRequest())
            .doctorIdRequest(request.getDoctorIdRequest())
            .requestSpeciality(specialization)
            .requestDate(DateTimeParser.parseDateTime(request.getRequestDate()))
            .isValid(true)
            .requestDetail(request.getRequestDetail())
            .build();
    }
}
