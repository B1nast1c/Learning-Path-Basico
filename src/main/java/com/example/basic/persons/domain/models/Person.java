package com.example.basic.persons.domain.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @Indexed
    @NotBlank(message = "Personal ID must not be empty")
    @Pattern(regexp = "^\\d{8}$", message = "Personal ID must contain exactly 8 numeric digits")
    protected String personID;
    protected String personAppID;
    @NotBlank(message = "Name must not be empty")
    protected String personName;
    @NotBlank(message = "Surname must not be empty")
    protected String personSurname;
    @NotBlank(message = "Role must not be empty")
    protected AppRoles personRole;
    protected boolean isActive;
}
