package com.example.basic.appointments.application.utils;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Clase encargada de convertir objetos de tipo Appointment (modelo de dominio)
 * a objetos de tipo AppointmentsResponse (modelo de salida).
 */
@Component
public class AppointmentMapper {

    private final ModelMapper mapper;

    /**
     * Constructor que recibe un ModelMapper específico para citas.
     *
     * @param modelMapper Mapper configurado para convertir objetos Appointment.
     */
    public AppointmentMapper(@Qualifier("appointmentMapper") ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    /**
     * Convierte una sola cita en una respuesta.
     *
     * @param request La cita a convertir.
     * @return Objeto de respuesta con los datos de la cita y un mensaje de estado.
     */
    public AppointmentsResponse mapAppointmentToResponse(Appointment request) {
        AppointmentsResponse response = mapper.map(request, AppointmentsResponse.class);
        response.setRequestDetail("APPOINTMENT STATUS: " + request.getAppointmentStatus().toString());
        return response;
    }

    /**
     * Convierte una lista de citas en una lista de respuestas.
     *
     * @param appointments Lista de citas a convertir.
     * @return Lista de respuestas con los datos de cada cita y su estado.
     */
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
