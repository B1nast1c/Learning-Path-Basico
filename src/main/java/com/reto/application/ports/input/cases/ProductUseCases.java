package com.reto.application.ports.input.cases;

import com.reto.infrastructure.adapters.models.dto.ProductDTO;

import java.util.List;

public interface ProductUseCases {
    List<ProductDTO> getProducts();
}
