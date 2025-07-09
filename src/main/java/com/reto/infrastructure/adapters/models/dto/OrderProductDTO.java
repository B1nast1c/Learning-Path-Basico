package com.reto.infrastructure.adapters.models;

import com.reto.domain.models.OrderProductId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class OrderProductDTO {
    private OrderProductId id = new OrderProductId();
    private OrderDTO order;
    private ProductDTO product;
    private int productQuantity;
}
