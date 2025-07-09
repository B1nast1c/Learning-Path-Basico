package com.reto.infrastructure.utils.mappers;

import com.reto.domain.models.OrderProduct;
import com.reto.infrastructure.adapters.models.dto.OrderProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMapper {
    private final ModelMapper mapper;

    public OrderProductMapper(ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    public OrderProductDTO mapOrderProductToDTO(OrderProduct orderProduct) {
        return mapper.map(orderProduct, OrderProductDTO.class);
    }
}
