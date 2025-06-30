package com.example.basic.config;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.Appointment;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para los mapeadores (ModelMapper).
 * Define cómo se deben convertir los objetos entre capas, especialmente entre
 * modelos de dominio y modelos de respuesta.
 */
@Configuration
public class MapperConfig {

    /**
     * Bean que configura un ModelMapper específico para mapear objetos de tipo Appointment
     * a AppointmentsResponse.
     *
     * @return ModelMapper configurado.
     */
    @Bean
    @Qualifier("appointmentMapper")
    public ModelMapper appointmentModelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Define cómo mapear manualmente los campos entre Appointment y AppointmentsResponse
        mapper.addMappings(new PropertyMap<Appointment, AppointmentsResponse>() {
            @Override
            protected void configure() {
                map().setRequestId(source.getRequestId());
                map().setRequestDate(source.getAppointmentDate());
                map().setPatientIdRequest(source.getPatientId());
                map().setPatientFullName(source.getPatientFullName());
                map().setDoctorIdRequest(source.getDoctorId());
                map().setDoctorFullName(source.getDoctorFullName());
            }
        });

        return mapper;
    }

    /**
     * Bean genérico para mapear objetos relacionados con personas.
     *
     * @return ModelMapper sin configuración personalizada.
     */
    @Bean
    @Qualifier("personMapper")
    public ModelMapper personModelMapper() {
        return new ModelMapper();
    }
}
