package com.reto.application.ports.input.cases;

public interface OrderUseCases {
    void updateProductQuantityInOrder(long orderId, long productId, int newQuantity);
}
