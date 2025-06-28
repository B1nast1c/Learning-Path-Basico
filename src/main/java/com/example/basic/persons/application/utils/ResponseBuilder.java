package com.example.basic.persons.application.utils;

import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.models.PersonsResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class ResponseBuilder {
    public static <T> Mono<GenericResponse> buildListResponse(Flux<T> source, Function<List<T>, List<PersonsResponse>> mapper) {
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

    public static <T> Mono<GenericResponse> buildSingleResponse(Mono<T> source, Function<T, PersonsResponse> mapper) {
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

    public static <T> Mono<GenericResponse> buildSavedResponse(Mono<T> source, Function<T, PersonsResponse> mapper) {
        return source
                .flatMap(item ->
                        Mono.just(GenericResponse.builder()
                            .responseStatus(Constants.VALID)
                            .details("Saving changes in data")
                            .data(List.of(mapper.apply(item)))
                            .build()))
                .onErrorResume(error ->
                        Mono.just(GenericResponse
                            .builder()
                            .responseStatus(Constants.ERROR)
                            .details(error.getMessage())
                            .data(List.of())
                            .build()));
    }
}
