package com.example.basic.appointments.application.services;

import com.example.basic.appointments.application.ports.input.cases.AppointmentRequestUseCases;
import com.example.basic.appointments.application.ports.input.cases.AppointmentUseCases;
import com.example.basic.appointments.application.ports.input.models.RequestModel;
import com.example.basic.appointments.application.ports.output.models.GenericResponse;
import com.example.basic.appointments.application.ports.output.repository.RequestRepositoryInterface;
import com.example.basic.appointments.application.utils.AppointmentMapper;
import com.example.basic.appointments.application.utils.AppointmentRequestMapper;
import com.example.basic.appointments.application.utils.Constants;
import com.example.basic.appointments.application.utils.builders.AppointmentBuilder;
import com.example.basic.appointments.application.utils.builders.RequestBuilder;
import com.example.basic.appointments.application.utils.builders.ResponseBuilder;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.models.AppointmentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class RequestService implements AppointmentRequestUseCases {
    private final RequestRepositoryInterface requestRepository;
    private final AppointmentRequestMapper requestMapper;
    private final AppointmentUseCases appointmentService;

    public RequestService(
        RequestRepositoryInterface repositoryInterface,
        AppointmentRequestMapper mapper,
        AppointmentUseCases appointmentService) {
        this.requestRepository = repositoryInterface;
        this.requestMapper = mapper;
        this.appointmentService = appointmentService;
    }

    @Override
    public Mono<GenericResponse> createRequest(RequestModel request) {
        return Mono.fromCallable(() -> RequestBuilder.buildAppointmentRequest(request))
            .flatMap(buildRequest ->
                requestRepository.createRequest(buildRequest)
                    .filter(AppointmentRequest::isValid)
                    .flatMap(filteredRequest -> {
                        Appointment buildAppointment = AppointmentBuilder.buildAppointmentFromRequest(filteredRequest);
                        return appointmentService.createAppointment(buildAppointment);
                    })
                    .thenReturn(buildRequest))
            .flatMap(createdRequest ->
                        ResponseBuilder.buildSavedResponse(
                            Mono.just(createdRequest),
                            requestMapper
                        ))
            .onErrorResume(error -> Mono.just(
                GenericResponse.builder()
                    .responseStatus(Constants.ERROR)
                    .data(List.of())
                    .details(error.getMessage())
                    .build()
            ));
    }
}
