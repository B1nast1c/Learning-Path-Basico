package com.example.basic.persons.application.utils.builders;

import com.example.basic.persons.application.ports.ouput.models.GenericResponse;
import com.example.basic.persons.application.ports.ouput.models.PersonsResponse;
import com.example.basic.persons.application.utils.Constants;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseBuilderSimpleTest {

    @Test
    void testBuildSavedResponse() {
        Mono<String> source = Mono.just("TEST");

        GenericResponse response = ResponseBuilder.buildSavedResponse(
            source,
            value -> PersonsResponse.builder().personName(value).build()
        ).block();

        assert response != null;
        assertEquals(Constants.VALID, response.getResponseStatus());
        assertEquals("Saving changes in data", response.getDetails());
        assertEquals(1, response.getData().size());
    }

    @Test
    void testBuildSavedResponseError() {
        Mono<String> source = Mono.error(new RuntimeException("Error when saving"));

        GenericResponse response = ResponseBuilder.buildSavedResponse(
            source,
            value -> PersonsResponse.builder().personName(value).build()
        ).block();

        assert response != null;
        assertEquals(Constants.ERROR, response.getResponseStatus());
        assertEquals("Error when saving", response.getDetails());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void testBuildListResponse() {
        Flux<String> source = Flux.just("TEST", "ENTITY");

        GenericResponse response = ResponseBuilder.buildListResponse(
            source,
            list -> list.stream()
                .map(name -> PersonsResponse.builder().personName(name).build())
                .toList()
        ).block();

        assert response != null;
        assertEquals(Constants.VALID, response.getResponseStatus());
        assertEquals(2, response.getData().size());
    }

    @Test
    void testBuildListResponseError() {
        Flux<String> source = Flux.error(new RuntimeException("Error when querying"));

        GenericResponse response = ResponseBuilder.buildListResponse(
            source,
            list -> List.of()
        ).block();

        assert response != null;
        assertEquals(Constants.ERROR, response.getResponseStatus());
        assertEquals("Error when querying", response.getDetails());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void testBuildSingleResponse() {
        Mono<String> source = Mono.just("TEST");

        GenericResponse response = ResponseBuilder.buildSingleResponse(
            source,
            name -> PersonsResponse.builder().personName(name).build()
        ).block();

        assert response != null;
        assertEquals(Constants.VALID, response.getResponseStatus());
        assertEquals(1, response.getData().size());
    }

    @Test
    void testBuildSingleResponseError() {
        Mono<String> source = Mono.error(new RuntimeException("Not found"));

        GenericResponse response = ResponseBuilder.buildSingleResponse(
            source,
            value -> PersonsResponse.builder().personName(value).build()
        ).block();

        assert response != null;
        assertEquals(Constants.ERROR, response.getResponseStatus());
        assertEquals("Not found", response.getDetails());
        assertTrue(response.getData().isEmpty());
    }

}
