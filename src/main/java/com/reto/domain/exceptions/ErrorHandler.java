package com.reto.domain.exceptions;

import com.reto.domain.exceptions.throwables.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de errores para la aplicación.
 * Esta clase captura excepciones específicas lanzadas en cualquier parte del sistema
 * y devuelve una respuesta personalizada con un tipo de error definido.
 */
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Maneja errores cuando no se encuentra una entidad.
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleNotFoundOrder(OrderNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    /**
     * Maneja errores cuando no se encuentra una entidad.
     */
    @ExceptionHandler(ProductNotFoundExceptionInOrder.class)
    public ResponseEntity<String> handleNotFoundProductInOrder(ProductNotFoundExceptionInOrder e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    /**
     * Maneja errores sobre cantidades.
     */
    @ExceptionHandler({InvalidQuantityException.class})
    public ResponseEntity<String> handleInvalidQuantity(InvalidQuantityException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Maneja errores sobre cantidades.
     */
    @ExceptionHandler(ExceededQuantityException.class)
    public ResponseEntity<String> handleExceededQuantity(ExceededQuantityException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Maneja errores durante la actualización.
     */
    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<String> handleUpdateError(UpdateFailedException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    /**
     * Maneja errores genéricos.
     */
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<String> handleGenericError(GenericException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
