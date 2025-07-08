package com.example.basic.appointments.infrastructure.repositories;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Repositorio para acceder a las solicitudes de citas médicas en MongoDB.
 * Esta interfaz permite realizar operaciones reactivas sobre la colección de solicitudes
 */
@Repository
public interface RequestRepository extends ReactiveMongoRepository<AppointmentRequest, String> {

    /**
     * Verifica si existe una solicitud con el ID proporcionado.
     *
     * @param requestId ID de la solicitud.
     * @return Mono que emite true si existe, false si no.
     */
    Mono<Boolean> existsByRequestId(String requestId);

    /**
     * Busca solicitudes de cita por ID del doctor y fecha de la solicitud.
     *
     * @param doctorIdRequest ID del doctor.
     * @param requestDate Fecha y hora de la solicitud.
     * @return Lista reactiva de solicitudes que coinciden con los criterios.
     */
    Flux<AppointmentRequest> findByDoctorIdRequestAndRequestDate(String doctorIdRequest, LocalDateTime requestDate);
}
