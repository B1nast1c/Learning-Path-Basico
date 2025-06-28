package com.example.basic.persons.domain.models;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@CompoundIndex(name = "doctor_fullName", def = "{'personName': 1, 'personSurname': 1}")
@Document(collection = "Doctors")
public class Doctor extends Person {
    @Indexed
    @NotBlank(message = "Specialization must not be empty")
    private String specialization;
}
