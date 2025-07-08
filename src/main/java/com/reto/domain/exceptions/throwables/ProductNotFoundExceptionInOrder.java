package com.reto.domain.exceptions.throwables;

public class ProductNotFoundExceptionInOrder extends RuntimeException {
    public ProductNotFoundExceptionInOrder(String message) {
        super(message);
    }
}
