package com.example.basic.appointments.domain.services;

import com.example.basic.appointments.domain.models.Appointment;
import com.example.basic.appointments.domain.validations.DateValidations;
import com.example.basic.exceptions.throwables.DateFormatExc;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Servicio encargado de validar fechas relacionadas con citas médicas.
 * Se asegura de que las fechas estén en un rango válido y dentro del horario permitido.
 */
@Service
public class AppointmentValidationService {

    /**
     * Valida que el rango de fechas sea correcto.
     *
     * @param start Fecha de inicio.
     * @param end Fecha de fin.
     * @throws DateFormatExc si el rango de fechas no es válido.
     */
    public void validateDateRange(LocalDate start, LocalDate end) {
        if (!DateValidations.validateDateRanges(start, end)) {
            throw new DateFormatExc("Invalid date range");
        }
    }

    /**
     * Valida que la fecha de una cita sea válida.
     * Verifica que el año esté dentro del rango permitido y que la hora esté dentro del horario laboral.
     *
     * @param appointment La cita a validar.
     * @throws DateFormatExc si la fecha o la hora no son válidas.
     */
    public void validateAppointmentDate(Appointment appointment) {
        if (!DateValidations.isYearValid(appointment.getAppointmentDate().getYear())) {
            throw new DateFormatExc("The appointment year must be between 1900 and 2100");
        }

        if (!DateValidations.validateHour(appointment.getAppointmentDate())) {
            throw new DateFormatExc("The appointment must be during working hours");
        }
    }
}
