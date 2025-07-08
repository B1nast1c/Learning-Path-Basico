package com.reto.infrastructure.adapters.output.repositories;

import com.reto.application.ports.output.repository.OrderRepositoryInterface;
import com.reto.infrastructure.repositories.OrderRepository;
import com.reto.infrastructure.repositories.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImp implements OrderRepositoryInterface {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderRepositoryImp(OrderRepository order, ProductRepository product) {
        this.orderRepository = order;
        this.productRepository = product;
    }

    @Override
    public void updateProductInOrder(long orderId, long productId, int quantity) {
        // Lógica de negocio
        // Busqueda de orden
        // Busqueda de productos
        // Validaciones adicionales
    }
}
