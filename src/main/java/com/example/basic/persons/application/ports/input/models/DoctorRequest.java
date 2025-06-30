package com.example.basic.persons.application.ports.input.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoctorRequest {
    private String personID;
    private String personName;
    private String personSurname;
    private String specialization;
    private boolean isActive;
}
