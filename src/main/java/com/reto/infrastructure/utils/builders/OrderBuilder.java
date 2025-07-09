package com.reto.infrastructure.utils.builders;

import com.reto.domain.models.Order;
import com.reto.infrastructure.adapters.models.dto.OrderDTO;
import com.reto.infrastructure.utils.mappers.OrderProductMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderBuilder {
    private final OrderProductMapper orderProductMapper;

    public OrderBuilder(OrderProductMapper mapper) {
        this.orderProductMapper = mapper;
    }

    public OrderDTO buildOrderDTO(Order order) {
        return OrderDTO.builder()
            .id(order.getId())
            .products(
                order.getProducts()
                    .stream()
                    .map(orderProductMapper::mapOrderProductToDTO)
                    .toList()
            )
            .build();
    }
}
