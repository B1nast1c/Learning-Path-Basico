package com.reto.domain.exceptions;

import com.reto.domain.exceptions.throwables.*;
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
    @ExceptionHandler(OrderNotFoundException.class)
    public CustomError handleNotFoundOrder(OrderNotFoundException e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.ORDER_NOT_FOUND);
    }

    /**
     * Maneja errores cuando no se encuentra una entidad.
     */
    @ExceptionHandler(ProductNotFoundExceptionInOrder.class)
    public CustomError handleNotFoundProductInOrder(ProductNotFoundExceptionInOrder e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.PRODUCT_NOT_FOUND);
    }

    /**
     * Maneja errores sobre cantidades.
     */
    @ExceptionHandler(InvalidQuantityException.class)
    public CustomError handleInvalidQuantity(InvalidQuantityException e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.INVALID_QUANTITY);
    }

    /**
     * Maneja errores sobre cantidades.
     */
    @ExceptionHandler(ExceededQuantityException.class)
    public CustomError handleExceededQuantity(ExceededQuantityException e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.EXCEEDED_QUANTITY);
    }

    /**
     * Maneja errores durante la actualización.
     */
    @ExceptionHandler(UpdateFailedException.class)
    public CustomError handleUpdateError(UpdateFailedException e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.UPDATE_ERROR);
    }

    /**
     * Maneja errores genéricos.
     */
    @ExceptionHandler(GenericException.class)
    public CustomError handleGenericError(GenericException e) {
        return new CustomError(e.getMessage(), CustomError.ErrorType.GENERIC_ERROR);
    }
}
