package com.reto.infrastructure.adapters.input.controllers;

import com.reto.application.ports.input.models.UpdateRequestModel;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expone los endpoints relacionados con las órdenes conformadas por productos.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    public OrderController() {
    }

    /**
     * Crea una nueva orden.
     */
    @PostMapping("/create")
    public void createOrder() {
    }

    /**
     * Crea una nueva orden.
     */
    @PatchMapping("/update")
    public void updateQuantity(@RequestBody UpdateRequestModel updateRequest) {
    }
}