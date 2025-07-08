package com.example.basic.appointments.application.ports.output.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GenericResponse {
    private String responseStatus;
    private String details;
    private List<AppointmentsResponse> data;
}
