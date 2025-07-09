package com.reto.infrastructure.adapters.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RequestDTO {
    private long orderId;
    private long productId;
    private int productQuantity;
}
