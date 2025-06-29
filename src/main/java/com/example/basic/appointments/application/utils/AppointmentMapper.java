package com.example.basic.appointments.application.utils;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentMapper {
    private final ModelMapper mapper;

    public AppointmentMapper(@Qualifier("appointmentMapper") ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    public <T> AppointmentsResponse mapAppointmentToResponse(Appointment request) {
        AppointmentsResponse response = mapper.map(request, AppointmentsResponse.class);
        response.setRequestDetail("APPOINTMENT STATUS: " + request.getAppointmentStatus().toString());
        return response;
    }

    public List<AppointmentsResponse> mapAppointmentsToResponse(List<Appointment> appointments) {
        return appointments.stream()
            .map(appointment -> {
                AppointmentsResponse response = mapper.map(appointment, AppointmentsResponse.class);
                response.setRequestDetail("APPOINTMENT STATUS: " + appointment.getAppointmentStatus().toString());
                return response;
            })
            .toList();
    }

}
