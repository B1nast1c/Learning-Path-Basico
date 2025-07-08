package com.example.basic.persons.application.ports.ouput.models;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
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
