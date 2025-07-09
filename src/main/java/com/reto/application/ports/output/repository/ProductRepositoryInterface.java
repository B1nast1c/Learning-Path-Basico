package com.reto.application.ports.output.repository;

import com.reto.domain.models.Product;

import java.util.List;

public interface ProductRepositoryInterface {
    List<Product> findAllOrders();
}
