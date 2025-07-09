package com.reto.domain.validations;

public class ProductsValidation {
    private static boolean validateProductQuantity(int currentQuantity, int requestedQuantity) {
        return requestedQuantity <= currentQuantity;
    }

    private static boolean validateProductNotZero(int currentQuantity) {
        return currentQuantity < 0;
    }

    
}
