package com.example.basic.persons.application.utils.builders;

import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.models.PersonsResponse;
import com.example.basic.persons.application.utils.Constants;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

/**
 * Clase utilitaria para construir respuestas estándar del sistema.
 */
public class ResponseBuilder {

    /**
     * Construye una respuesta con una lista de elementos.
     *
     * @param source Flux con los datos originales.
     * @param mapper Función que transforma la lista de datos en una lista de respuestas.
     * @param <T> Tipo de los datos originales.
     * @return Mono con una respuesta genérica que contiene la lista transformada.
     */
    public static <T> Mono<GenericResponse> buildListResponse(
        Flux<T> source,
        Function<List<T>, List<PersonsResponse>> mapper) {
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

    /**
     * Construye una respuesta con un solo elemento.
     *
     * @param source Mono con el dato original.
     * @param mapper Función que transforma el dato en una respuesta.
     * @param <T> Tipo del dato original.
     * @return Mono con una respuesta genérica que contiene el dato transformado.
     */
    public static <T> Mono<GenericResponse> buildSingleResponse(
        Mono<T> source,
        Function<T, PersonsResponse> mapper) {
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

    /**
     * Construye una respuesta para una operación de guardado o modificación.
     *
     * @param source Mono con el dato guardado.
     * @param mapper Función que transforma el dato en una respuesta.
     * @param <T> Tipo del dato original.
     * @return Mono con una respuesta genérica que contiene el dato transformado.
     */
    public static <T> Mono<GenericResponse> buildSavedResponse(
        Mono<T> source,
        Function<T, PersonsResponse> mapper) {
        return source
            .flatMap(item ->
                Mono.just(GenericResponse.builder()
                    .responseStatus(Constants.VALID)
                    .details("Saving changes in data")
                    .data(List.of(mapper.apply(item)))
                    .build()))
            .onErrorResume(error ->
                Mono.just(GenericResponse.builder()
                    .responseStatus(Constants.ERROR)
                    .details(error.getMessage())
                    .data(List.of())
                    .build()));
    }
}