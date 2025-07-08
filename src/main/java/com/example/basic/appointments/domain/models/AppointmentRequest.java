package com.example.basic.appointments.domain.models;

import com.example.basic.persons.domain.models.Specializations;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "Requests")
public class AppointmentRequest {
    @Id
    @Indexed
    private String requestId;
    @Indexed
    private String patientIdRequest;
    private String patientFullName;
    private Specializations requestSpeciality;
    @Indexed
    private String doctorIdRequest;
    private String doctorFullName;
    private LocalDateTime requestDate;
    private boolean isValid;
    private String requestDetail;
}
