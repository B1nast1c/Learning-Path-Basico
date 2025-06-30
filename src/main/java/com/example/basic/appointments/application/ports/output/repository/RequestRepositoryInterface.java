package com.example.basic.appointments.application.ports.output.repository;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import reactor.core.publisher.Mono;

/**
 * Esta interfaz define cómo se guarda una solicitud de cita en la base de datos.
 * Es parte de la capa de salida (output), que se encarga de comunicarse con el almacenamiento de datos.
 */
public interface RequestRepositoryInterface {

    /**
     * Crea y guarda una nueva solicitud de cita.
     *
     * @param request Datos de la solicitud que se quiere guardar.
     * @return La solicitud guardada.
     */
    Mono<AppointmentRequest> createRequest(AppointmentRequest request);
}
