package com.example.basic.appointments.application.utils.builders;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import com.example.basic.appointments.application.utils.AppointmentRequestMapper;
import com.example.basic.appointments.application.utils.Constants;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class ResponseBuilder {
    public static <T> Mono<GenericResponse> buildListResponse(Flux<T> source, Function<List<T>, List<AppointmentsResponse>> mapper) {
        return source
                .collectList()
                .flatMap(list ->
                        Mono.just(GenericResponse.builder()
                            .responseStatus(Constants.VALID)
                            .details("Getting list of entities")
                            .data(mapper.apply(list))
                            .build()))
                .onErrorResume(error ->
                        Mono.just(GenericResponse.builder()
                            .responseStatus(Constants.ERROR)
                            .details(error.getMessage())
                            .data(List.of())
                            .build()));
    }

    public static <T> Mono<GenericResponse> buildSingleResponse(Mono<T> source, Function<T, AppointmentsResponse> mapper) {
        return source
                .flatMap(item ->
                        Mono.just(GenericResponse.builder()
                            .responseStatus(Constants.VALID)
                            .details("Getting entity")
                            .data(List.of(mapper.apply(item)))
                            .build()))
                .onErrorResume(error ->
                        Mono.just(GenericResponse.builder()
                            .responseStatus(Constants.ERROR)
                            .details(error.getMessage())
                            .data(List.of())
                            .build()));
    }

    public static Mono<GenericResponse> buildSavedResponse(Mono<AppointmentRequest> source, AppointmentRequestMapper mapper) {
        return source
                .flatMap((AppointmentRequest item) ->
                        Mono.just(GenericResponse.builder()
                            .responseStatus(Constants.VALID)
                            .details("Saving changes in data")
                            .data(List.of(mapper.mapAppointmentRequestToResponse(item)))
                            .build()))
                .onErrorResume(error ->
                        Mono.just(GenericResponse
                            .builder()
                            .responseStatus(Constants.ERROR)
                            .details(error.getMessage())
                            .data(List.of())
                            .build()));
    }

    public static Mono<AppointmentsResponse> buildSavedAppointmentResponse(Mono<Appointment> source) {
        return source
            .map(appointment -> AppointmentsResponse.builder()
            .requestId(appointment.getRequestId())
            .requestDate(appointment.getAppointmentDate())
            .patientIdRequest(appointment.getPatientId())
            .doctorIdRequest(appointment.getDoctorId())
            .requestDetail("APPOINTMENT STATUS: " + appointment.getAppointmentStatus())
            .build());
    }
}
