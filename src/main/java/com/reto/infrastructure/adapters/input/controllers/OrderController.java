package com.reto.infrastructure.adapters.input.controllers;

import com.reto.application.ports.input.cases.OrderUseCases;
import com.reto.infrastructure.adapters.models.dto.OrderDTO;
import com.reto.infrastructure.adapters.models.request.RequestDTO;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST que expone los endpoints relacionados con las órdenes conformadas por productos.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderUseCases orderService;

    public OrderController(OrderUseCases useCases) {
        this.orderService = useCases;
    }

    /**
     * Crea una nueva orden.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createOrder() {
        return ResponseEntity.ok().body("Order Created");
    }

    /**
     * Crea una nueva orden.
     */
    @PatchMapping("/update")
    public ResponseEntity<String> updateQuantity(@RequestBody RequestDTO updateRequest) {
        orderService.updateProductQuantityInOrder(updateRequest.getOrderId(),
            updateRequest.getProductId(),
            updateRequest.getProductQuantity());

        return ResponseEntity.ok().body("Quantity updated successfully");
    }

    /**
     * Visualiza órdenes.
     */
    @GetMapping("/view")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return ResponseEntity.ok().body(orderService.getOrders());
    }
}