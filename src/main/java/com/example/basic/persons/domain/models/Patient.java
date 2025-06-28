package com.example.basic.persons.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "Patients")
@CompoundIndex(name = "patient_fullName", def = "{'personName': 1, 'personSurname': 1}")
public class Patient extends Person {
    @Indexed
    @NotBlank(message = "Birthdate must not be empty")
    @PastOrPresent(message = "Birthdate must be before today")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
}
