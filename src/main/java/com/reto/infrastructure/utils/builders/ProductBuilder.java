package com.reto.infrastructure.utils.builders;

import com.reto.domain.models.Order;
import com.reto.infrastructure.adapters.models.dto.OrderDTO;

public class OrderBuilder {
    public static OrderDTO buildOrderDTO(Order order) {
        return OrderDTO.builder()
            .id(order.getId())
            .products(order.getProducts())
            .build();
    }
}
