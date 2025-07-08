package com.reto.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
public class OrderProduct {
    @EmbeddedId
    private OrderProductId id = new OrderProductId();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private int productQuantity;

    public OrderProduct(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.productQuantity = quantity;
        this.id = new OrderProductId(order.getId(), product.getId());
    }
}
