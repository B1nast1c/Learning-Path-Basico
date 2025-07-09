package com.reto.infrastructure.services;

import com.reto.application.ports.output.repository.ProductRepositoryInterface;
import com.reto.application.services.ProductService;
import com.reto.infrastructure.adapters.models.dto.ProductDTO;
import com.reto.infrastructure.utils.builders.ProductBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
    private final ProductRepositoryInterface productRepository;
    private final ProductBuilder productBuilder;

    public ProductServiceImp(ProductRepositoryInterface repositoryInterface, ProductBuilder builder) {
        this.productRepository = repositoryInterface;
        this.productBuilder = builder;
    }

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAllOrders()
            .stream()
            .map(productBuilder::buildProductDTO)
            .toList();
    }
}
