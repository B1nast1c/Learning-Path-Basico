package com.example.basic.config;

import com.example.basic.appointments.application.ports.output.models.AppointmentsResponse;
import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.persons.domain.models.Specializations;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    @Qualifier("appointmentMapper")
    public ModelMapper appointmentModelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new PropertyMap<Appointment, AppointmentsResponse>() {
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

    @Bean
    @Qualifier("personMapper")
    public ModelMapper personModelMapper() {
        return new ModelMapper();
    }
}
