package com.reto.infrastructure.utils.builders;

import com.reto.domain.models.OrderProduct;
import com.reto.infrastructure.adapters.models.dto.OrderProductDTO;

public class OrderProductBuilder {
    public static OrderProductDTO buildOrderProductDTO(OrderProduct orderProduct) {
        return OrderProductDTO.builder()
            .id(orderProduct.getId())
            .productQuantity(orderProduct.getProductQuantity())
            .build();
    }
}
