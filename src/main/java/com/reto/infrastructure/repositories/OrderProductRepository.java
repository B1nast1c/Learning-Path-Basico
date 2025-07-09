package com.reto.infrastructure.repositories;

import com.reto.domain.models.OrderProduct;
import com.reto.domain.models.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    Optional<OrderProduct> findById_OrderIdAndId_ProductId(long orderId, long productId);
}
