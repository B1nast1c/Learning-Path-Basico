package com.reto.infrastructure.adapters.input.controllers;

import com.reto.application.ports.input.cases.ProductUseCases;
import com.reto.infrastructure.adapters.models.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductUseCases productUseCases;

    public ProductController(ProductUseCases useCases) {
        this.productUseCases = useCases;
    }

    @GetMapping("/view")
    public ResponseEntity<List<ProductDTO>> getOrders() {
        return ResponseEntity.ok().body(productUseCases.getProducts());
    }
}