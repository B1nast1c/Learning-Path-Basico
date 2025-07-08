package com.example.basic.appointments.application.utils.builders;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Generador de índices personalizados para solicitudes y citas médicas..
 */
@Slf4j
public class IndexBuilder {
    public static String generateRequestId(String requestDate, String patientId, String doctorId) {
        String[] parts = requestDate.split(" ");
        String datePart = parts[0].replace("/", "");
        String hourPart = parts[1].split(":")[0];
        return String.format("REQ-%s-%s-%s-%s", patientId, doctorId, datePart, hourPart);
    }

    public static String generateAppointmentId(String appointmentDate, String patientId, String doctorId) {
        LocalDateTime mappedDate = LocalDateTime.parse(appointmentDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String datePart = mappedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replace("-", "");
        String hourPart = mappedDate.format(DateTimeFormatter.ofPattern("HH"));
        return String.format("APT-%s-%s-%s-%s", patientId, doctorId, datePart, hourPart);
    }
}
