package com.reto.infrastructure.utils.builders;

import com.reto.domain.models.Order;
import com.reto.domain.models.Product;
import com.reto.infrastructure.adapters.models.dto.OrderDTO;
import com.reto.infrastructure.adapters.models.dto.ProductDTO;

public class ProductBuilder {
    public static ProductDTO buildProductDTO(Product product) {
        return ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .availableQuantity(product.getAvailableQuantity())
            .orderProducts(p)
            .build();
    }
}
