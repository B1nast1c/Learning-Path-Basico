package com.reto.infrastructure.utils.builders;

import com.reto.domain.models.Product;
import com.reto.infrastructure.adapters.models.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductBuilder {
    public ProductDTO buildProductDTO(Product product) {
        return ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .availableQuantity(product.getAvailableQuantity())
            .build();
    }
}
