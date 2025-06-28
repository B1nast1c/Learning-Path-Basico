package com.example.basic.persons.application.ports.input.models;

import lombok.Data;

@Data
public class PatientRequest {
    private String personID;
    private String personName;
    private String personSurname;
    private String birthDate;
    private boolean isActive;
}
