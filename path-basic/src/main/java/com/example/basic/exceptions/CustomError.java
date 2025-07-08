package com.example.basic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {
    private String errorMessage;
    private ErrorType errorType;

    public enum ErrorType {
        NOT_FOUND,
        DUPLICATE_ENTITY,
        TAKEN_APPOINTMENT,
        INVALID_DATE,
        INVALID_SPECIALITY,
        GENERIC_ERROR,
    }
}
