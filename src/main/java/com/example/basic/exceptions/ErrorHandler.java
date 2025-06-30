package com.example.basic.exceptions;

import com.example.basic.exceptions.throwables.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador global de errores para la aplicación.
 * Esta clase captura excepciones específicas lanzadas en cualquier parte del sistema
 * y devuelve una respuesta personalizada con un tipo de error definido.
 */
@ControllerAdvice
public class ErrorHandler {

    /**
     * Maneja errores cuando no se encuentra una entidad.
     */
    @ExceptionHandler(NotFoundExc.class)
    public CustomError handleNotFoundEntity(NotFoundExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.NOT_FOUND);
    }

    /**
     * Maneja errores cuando se intenta registrar una entidad duplicada.
     */
    @ExceptionHandler(DuplicateExc.class)
    public CustomError handleDuplicateRegister(DuplicateExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.DUPLICATE_ENTITY);
    }

    /**
     * Maneja errores cuando una cita ya está ocupada.
     */
    @ExceptionHandler(TakenAppointmentExc.class)
    public CustomError handleTakenAppointment(TakenAppointmentExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.TAKEN_APPOINTMENT);
    }

    /**
     * Maneja errores cuando la especialidad del doctor no coincide con la solicitada.
     */
    @ExceptionHandler(SpecialityExc.class)
    public CustomError handleInvalidSpeciality(SpecialityExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.INVALID_SPECIALITY);
    }

    /**
     * Maneja errores relacionados con fechas inválidas.
     */
    @ExceptionHandler(DateFormatExc.class)
    public CustomError handleInvalidDate(DateFormatExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.INVALID_DATE);
    }

    /**
     * Maneja errores genéricos no clasificados.
     */
    @ExceptionHandler(GenericExc.class)
    public CustomError handleGenericError(GenericExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.GENERIC_ERROR);
    }
}
