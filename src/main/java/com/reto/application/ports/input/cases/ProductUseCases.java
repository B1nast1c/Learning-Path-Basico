package com.reto.application.ports.input.cases;

import com.reto.infrastructure.adapters.models.dto.OrderDTO;

import java.util.List;

public interface OrderUseCases {
    void updateProductQuantityInOrder(long orderId, long productId, int newQuantity);
    List<OrderDTO> getOrders();
}
