package com.example.basic.appointments.domain.services;

import com.example.basic.appointments.domain.models.AppointmentRequest;
import com.example.basic.appointments.domain.validations.AppointmentValidations;
import com.example.basic.appointments.domain.validations.DateValidations;
import com.example.basic.exceptions.throwables.*;
import com.example.basic.persons.domain.models.Doctor;
import com.example.basic.persons.domain.models.Patient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio encargado de validar una solicitud de cita médica.
 * Verifica que los datos del paciente, del doctor y de la fecha sean correctos
 * antes de permitir que se cree la cita.
 */
@Service
public class AppointmentRequestValidationService {

    /**
     * Valida una solicitud de cita médica.
     *
     * @param request Datos de la solicitud de cita.
     * @param doctorMono Información del doctor (de forma reactiva).
     * @param patientMono Información del paciente (de forma reactiva).
     * @param existingAppointments Citas ya existentes para verificar conflictos de horario.
     * @return La solicitud validada si todo está correcto, o un error si hay algún problema.
     */
    public Mono<AppointmentRequest> validate(
        AppointmentRequest request,
        Mono<Doctor> doctorMono,
        Mono<Patient> patientMono,
        Flux<AppointmentRequest> existingAppointments) {

        // Verifica que el doctor y el paciente no sean la misma persona
        if (!AppointmentValidations.validatePersons(request.getDoctorIdRequest(), request.getPatientIdRequest())) {
            return Mono.error(new GenericExc("Doctor and patient must be different"));
        }

        // Verifica que el paciente exista
        return patientMono
            .switchIfEmpty(Mono.error(new NotFoundExc("No patient was found")))
            .flatMap(patient ->
                // Verifica que el doctor exista
                doctorMono
                    .switchIfEmpty(Mono.error(new NotFoundExc("No doctor was found")))
                    .flatMap(doctor -> {
                        // Verifica que la especialidad del doctor coincida con la solicitada
                        if (!AppointmentValidations.validateSpeciality(doctor, request.getRequestSpeciality().name())) {
                            return Mono.error(new SpecialityExc("The doctor's speciality does not match"));
                        }

                        // Verifica que el año de la cita esté en un rango válido
                        if (!DateValidations.isYearValid(request.getRequestDate().getYear())) {
                            return Mono.error(new DateFormatExc("The appointment year must be between 1900 and 2100"));
                        }

                        // Verifica que la hora de la cita esté dentro del horario laboral
                        if (!DateValidations.validateHour(request.getRequestDate())) {
                            return Mono.error(new DateFormatExc("The appointment request must be during working time"));
                        }

                        // Asigna los nombres completos del paciente y del doctor a la solicitud
                        request.setPatientFullName(patient.getPersonName() + " " + patient.getPersonSurname());
                        request.setDoctorFullName(doctor.getPersonName() + " " + doctor.getPersonSurname());

                        // Verifica si ya existe una cita en el mismo horario con el mismo doctor
                        return existingAppointments
                            .any(existing -> !DateValidations.validateDates(request.getRequestDate(), existing.getRequestDate()))
                            .flatMap(conflict -> {
                                if (conflict.equals(Boolean.TRUE)) {
                                    return Mono.error(new DuplicateExc("The doctor has its schedule taken"));
                                }
                                return Mono.just(request);
                            });
                    }));
    }
}
