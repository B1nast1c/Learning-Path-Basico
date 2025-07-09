package com.reto.infrastructure.services;

import com.reto.application.ports.output.repository.OrderRepositoryInterface;
import com.reto.application.services.OrderService;
import com.reto.infrastructure.adapters.models.dto.OrderDTO;
import com.reto.infrastructure.utils.builders.OrderBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderServiceImp implements OrderService {
    private final OrderRepositoryInterface orderRepository;
    private final OrderBuilder orderBuilder;

    public OrderServiceImp(OrderRepositoryInterface orderInterface, OrderBuilder builder) {
        this.orderRepository = orderInterface;
        this.orderBuilder = builder;
    }

    @Override
    public void updateProductQuantityInOrder(long orderId, long productId, int newQuantity) {
        orderRepository.updateProductInOrder(orderId, productId, newQuantity);
    }

    @Override
    public List<OrderDTO> getOrders() {
        return orderRepository.findAllOrders()
            .stream()
            .map(orderBuilder::buildOrderDTO)
            .toList();
    }
}
