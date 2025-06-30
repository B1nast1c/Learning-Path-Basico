package com.example.basic.appointments.application.utils;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentRequestMapper {
    private final ModelMapper mapper;

    public AppointmentRequestMapper(@Qualifier("appointmentMapper") ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    public AppointmentsResponse mapAppointmentRequestToResponse(AppointmentRequest request) {
        return mapper.map(request, AppointmentsResponse.class);
    }
}
