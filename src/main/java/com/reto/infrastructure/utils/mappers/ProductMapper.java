package com.reto.infrastructure.utils.mappers;

import com.reto.domain.models.Product;
import com.reto.infrastructure.adapters.models.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ModelMapper mapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    public ProductDTO mapProductToDTO(Product product) {
        return mapper.map(product, ProductDTO.class);
    }
}
