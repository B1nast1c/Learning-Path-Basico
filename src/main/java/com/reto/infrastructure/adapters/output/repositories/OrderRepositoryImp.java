package com.reto.infrastructure.adapters.output.repositories;

import com.reto.application.ports.output.repository.OrderRepositoryInterface;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImp implements OrderRepositoryInterface {
    @Override
    public void updateProductInOrder(long orderId, long productId, int quantity) {

    }
}
