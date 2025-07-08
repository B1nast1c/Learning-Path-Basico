package com.reto.domain.validations;

public class ProductsValidation {
    public static boolean validateProductQuantity(int currentQuantity, int requestedQuantity) {
        return requestedQuantity <= currentQuantity;
    }

    public static boolean validateProductNotZero(int currentQuantity) {
        return currentQuantity < 0;
    }
}
