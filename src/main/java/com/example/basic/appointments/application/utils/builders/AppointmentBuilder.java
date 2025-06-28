package com.example.basic.appointments.application.utils.builders;

import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.appointments.domain.models.AppointmentStatus;

public class AppointmentBuilder {
    public static Appointment buildAppointmentFromRequest(AppointmentRequest request) {
        return Appointment.builder()
            .appointmentId(IndexBuilder
                .generateAppointmentId(
                    String.valueOf(request.getRequestDate()),
                    request.getPatientIdRequest(),
                    request.getDoctorIdRequest()
                ))
            .requestId(request.getRequestId())
            .doctorId(request.getDoctorIdRequest())
            .doctorFullName(request.getDoctorFullName())
            .patientId(request.getPatientIdRequest())
            .patientFullName(request.getPatientFullName())
            .appointmentDate(request.getRequestDate())
            .appointmentStatus(AppointmentStatus.ACTIVE)
            .build();
    }
}
