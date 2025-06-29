package com.example.basic.appointments.domain.services;

import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.validations.DateValidations;
import com.example.basic.exceptions.throwables.DateFormatExc;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppointmentValidationService {
    public void validateDateRange(LocalDate start, LocalDate end) {
        if (!DateValidations.validateDateRanges(start, end)) {
            throw new DateFormatExc("Invalid date range");
        }
    }

    public void validateAppointmentDate(Appointment appointment) {
        if (!DateValidations.isYearValid(appointment.getAppointmentDate().getYear())) {
            throw new DateFormatExc("The appointment year must be between 1900 and 2100");
        }

        if (!DateValidations.validateHour(appointment.getAppointmentDate())) {
            throw new DateFormatExc("The appointment must be during working hours");
        }
    }
}
