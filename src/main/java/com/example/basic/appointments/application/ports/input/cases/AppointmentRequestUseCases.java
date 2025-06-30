package com.example.basic.appointments.application.ports.input.cases;

import com.example.basic.appointments.application.ports.input.models.RequestModel;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import reactor.core.publisher.Mono;

/**
 * Esta interfaz define lo que se puede hacer con las solicitudes de citas.
 */
public interface AppointmentRequestUseCases {

    /**
     * Crea una nueva solicitud de cita usando los datos que se le pasan.
     *
     * @param request Datos necesarios para hacer la solicitud (como nombre, fecha, etc.).
     * @return Una respuesta que indica si la solicitud fue exitosa o no.
     */
    Mono<GenericResponse> createRequest(RequestModel request);
}
