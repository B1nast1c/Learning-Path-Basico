package com.reto.domain.exceptions.throwables;

public class ExceededQuantityException extends RuntimeException {
    public ExceededQuantityException(String message) {
        super(message);
    }
}
