package com.reto.infrastructure.adapters.models;

import com.reto.domain.models.OrderProduct;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ProductDTO {
    private long id;
    private String name;
    private double price;
    private int availableQuantity;
    private List<OrderProductDTO> orderProducts;
}
