package com.reto.application.ports.output.repository;

public interface OrderRepositoryInterface {
    void updateProductInOrder(long orderId, long productId, int quantity);
}
