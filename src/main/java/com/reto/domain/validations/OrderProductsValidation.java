package com.reto.domain.validations;

import com.reto.domain.exceptions.throwables.ExceededQuantityException;
import com.reto.domain.exceptions.throwables.InvalidQuantityException;

public class OrderProductsValidation {
    private static boolean validateProductQuantity(int currentQuantity, int requestedQuantity) {
        return requestedQuantity > currentQuantity;
    }

    private static boolean validateProductLessThanZero(int requestedQuantity) {
        return requestedQuantity < 0;
    }

    public static void validate(int currentQuantity, int requestedQuantity) {
        if (validateProductQuantity(currentQuantity, requestedQuantity)) {
            throw new ExceededQuantityException("The requested product amount (" + requestedQuantity + ") exceeds the available amount (" + currentQuantity + ")");
        }
        if (validateProductLessThanZero(requestedQuantity)) {
            throw new InvalidQuantityException("The requested product amount is less than zero");
        }
    }
}
