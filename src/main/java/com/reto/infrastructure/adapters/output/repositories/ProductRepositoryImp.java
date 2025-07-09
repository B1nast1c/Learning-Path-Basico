package com.reto.infrastructure.adapters.output.repositories;

import com.reto.application.ports.output.repository.ProductRepositoryInterface;
import com.reto.domain.models.Order;
import com.reto.domain.models.Product;
import com.reto.infrastructure.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class ProductRepositoryImp implements ProductRepositoryInterface {
    private final ProductRepository productRepository;

    public ProductRepositoryImp(ProductRepository repository) {
        this.productRepository = repository;
    }

    @Override
    public List<Product> findAllOrders() {
        return productRepository.findAll();
    }
}
