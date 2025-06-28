package com.example.basic.persons.application.ports.ouput.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GenericResponse {
    private String responseStatus;
    private String details;
    private List<PersonsResponse> data;
}
