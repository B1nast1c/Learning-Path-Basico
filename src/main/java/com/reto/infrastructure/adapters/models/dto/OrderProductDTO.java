package com.reto.infrastructure.adapters.models.dto;

import com.reto.domain.models.OrderProductId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {
    private OrderProductId id;
    private int productQuantity;
}
