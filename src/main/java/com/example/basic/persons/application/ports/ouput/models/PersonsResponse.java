package com.example.basic.persons.application.ports.ouput.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonsResponse {
    private String personAppID;
    private String personID;
    private String personName;
    private String personSurname;
    private String personRole;
    private String personDetails;
}
