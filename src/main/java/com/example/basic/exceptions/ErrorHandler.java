package com.example.basic.exceptions;

import com.example.basic.exceptions.throwables.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundExc.class)
    public CustomError handleNotFoundEntity(NotFoundExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateExc.class)
    public CustomError handleDuplicateRegister(DuplicateExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.DUPLICATE_ENTITY);
    }

    @ExceptionHandler(TakenAppointmentExc.class)
    public CustomError handleTakenAppointment(TakenAppointmentExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.TAKEN_APPOINTMENT);
    }

    @ExceptionHandler(SpecialityExc.class)
    public CustomError handleInvalidSpeciality(SpecialityExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.INVALID_SPECIALITY);
    }

    @ExceptionHandler(DateFormatExc.class)
    public CustomError handleInvalidDate(DateFormatExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.INVALID_DATE);
    }

    @ExceptionHandler(GenericExc.class)
    public CustomError handleGenericError(GenericExc e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.GENERIC_ERROR);
    }
}