package com.reto.infrastructure.services;

import com.reto.application.services.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderServiceImp implements OrderService {
    @Override
    public void updateProductQuantityInOrder(long orderId, long productId, int newQuantity) {

    }
}
