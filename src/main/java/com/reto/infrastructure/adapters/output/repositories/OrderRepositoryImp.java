package com.reto.infrastructure.adapters.output.repositories;

import com.reto.application.ports.output.repository.OrderRepositoryInterface;
import com.reto.domain.exceptions.throwables.OrderNotFoundException;
import com.reto.domain.exceptions.throwables.ProductNotFoundExceptionInOrder;
import com.reto.domain.models.Order;
import com.reto.domain.models.OrderProduct;
import com.reto.domain.validations.OrderProductsValidation;
import com.reto.infrastructure.repositories.OrderProductRepository;
import com.reto.infrastructure.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class OrderRepositoryImp implements OrderRepositoryInterface {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderRepositoryImp(OrderRepository order, OrderProductRepository orderProduct) {
        this.orderRepository = order;
        this.orderProductRepository = orderProduct;
    }

    @Override
    public void updateProductInOrder(long orderId, long productId, int quantity) {
        // Lógica de negocio
        // Busqueda de orden
        // Busqueda de productos
        // Validaciones adicionales
        Order foundOrder = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("The requested order does not exist"));
        orderProductRepository.findById_OrderIdAndId_ProductId(foundOrder.getId(), productId)
            .map((OrderProduct foundProduct) -> {
                OrderProductsValidation.validate(foundProduct.getProductQuantity(), quantity);
                foundProduct.setProductQuantity(quantity);
                return foundProduct;
            })
            .map(orderProductRepository::save)
            .orElseThrow(() -> new ProductNotFoundExceptionInOrder("The selected product does not belong to the order"));
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
