package com.example.basic.appointments.application.utils;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentMapper {
    private final ModelMapper mapper;

    public AppointmentMapper(ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    public <T> AppointmentsResponse mapAppointmentToResponse(T request) {
        return mapper.map(request, AppointmentsResponse.class);
    }

    public List<AppointmentsResponse> mapAppointmentsToResponse(List<Appointment> appointments) {
        return appointments
            .stream()
            .map((Appointment appointment) -> mapper.map(appointment, AppointmentsResponse.class))
            .toList();
    }
}
