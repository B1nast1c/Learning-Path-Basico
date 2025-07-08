package com.reto.domain.exceptions;

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
        ORDER_NOT_FOUND,
        PRODUCT_NOT_FOUND,
        INVALID_QUANTITY,
        EXCEEDED_QUANTITY,
        UPDATE_ERROR,
        GENERIC_ERROR
    }
}
