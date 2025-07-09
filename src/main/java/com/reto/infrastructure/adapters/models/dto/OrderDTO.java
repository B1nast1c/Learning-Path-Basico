package com.reto.infrastructure.adapters.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderDTO {
    private long id;
    private List<OrderProductDTO> products;
}
