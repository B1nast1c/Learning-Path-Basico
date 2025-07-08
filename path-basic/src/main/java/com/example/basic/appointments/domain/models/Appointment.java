package com.example.basic.appointments.domain.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "Appointments")
public class Appointment {
    @Id
    private String appointmentId;
    @Indexed(unique = true)
    private String requestId;
    @Indexed
    private String doctorId;
    private String doctorFullName;
    @Indexed
    private String patientId;
    private String patientFullName;
    @Indexed
    private LocalDateTime appointmentDate;
    private String appointmentSpeciality;
    private AppointmentStatus appointmentStatus;
}
