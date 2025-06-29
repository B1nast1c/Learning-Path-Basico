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

@Service
public class AppointmentRequestValidationService {
    public Mono<AppointmentRequest> validate(AppointmentRequest request,
                                             Mono<Doctor> doctorMono,
                                             Mono<Patient> patientMono,
                                             Flux<AppointmentRequest> existingAppointments) {

        if (!AppointmentValidations.validatePersons(request.getDoctorIdRequest(), request.getPatientIdRequest())) {
            return Mono.error(new GenericExc("Doctor and patient must be different"));
        }
        return patientMono
            .switchIfEmpty(Mono.error(new NotFoundExc("No patient was found")))
            .flatMap(patient ->
                doctorMono
                    .switchIfEmpty(Mono.error(new NotFoundExc("No doctor was found")))
                    .flatMap(doctor -> {
                        if (!AppointmentValidations.validateSpeciality(doctor, request.getRequestSpeciality().name())) {
                            return Mono.error(new SpecialityExc("The doctor's speciality does not match"));
                        }

                        if (!DateValidations.isYearValid(request.getRequestDate().getYear())) {
                            return Mono.error(new DateFormatExc("The appointment year must be between 1900 and 2100"));
                        }

                        if (!DateValidations.validateHour(request.getRequestDate())) {
                            return Mono.error(new DateFormatExc("The appointment request must be during working time"));
                        }

                        request.setPatientFullName(patient.getPersonName() + " " + patient.getPersonSurname());
                        request.setDoctorFullName(doctor.getPersonName() + " " + doctor.getPersonSurname());

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


