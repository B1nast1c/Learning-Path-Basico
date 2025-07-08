package com.example.basic.appointments.application.utils;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Clase encargada de convertir una solicitud de cita (AppointmentRequest)
 * en un objeto de respuesta (AppointmentsResponse).
 */
@Component
public class AppointmentRequestMapper {

    private final ModelMapper mapper;

    /**
     * Constructor que recibe un ModelMapper configurado para citas.
     *
     * @param modelMapper Mapper usado para convertir objetos entre capas.
     */
    public AppointmentRequestMapper(@Qualifier("appointmentMapper") ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    /**
     * Convierte una solicitud de cita en una respuesta.
     *
     * @param request La solicitud de cita.
     * @return Objeto de respuesta con los datos convertidos.
     */
    public AppointmentsResponse mapAppointmentRequestToResponse(AppointmentRequest request) {
        return mapper.map(request, AppointmentsResponse.class);
    }
}
