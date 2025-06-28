package com.example.basic.persons.application.ports.input.models;

import lombok.Data;

@Data
public class DoctorRequest {
    private String personID;
    private String personName;
    private String personSurname;
    private String specialization;
    private boolean isActive;
}
