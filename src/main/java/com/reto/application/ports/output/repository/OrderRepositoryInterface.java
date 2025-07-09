package com.reto.application.ports.output.repository;

import com.reto.domain.models.Order;

import java.util.List;

public interface OrderRepositoryInterface {
    void updateProductInOrder(long orderId, long productId, int quantity);
    List<Order> findAllOrders();
}
