package com.example.basic.appointments.application.ports.input.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {
    private String patientIdRequest;
    private String requestSpeciality;
    private String doctorIdRequest;
    private String requestDate;
    private String requestDetail;
}
