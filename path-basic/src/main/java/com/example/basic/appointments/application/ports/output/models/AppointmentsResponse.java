package com.example.basic.appointments.application.ports.output.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsResponse {
    private String requestId;
    private LocalDateTime requestDate;
    private String patientIdRequest;
    private String patientFullName;
    private String doctorIdRequest;
    private String doctorFullName;
    private String requestDetail;
}
