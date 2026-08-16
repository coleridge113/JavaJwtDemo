package com.luna.jwt_demo.product.mapper;

import org.springframework.stereotype.Component;
import com.luna.jwt_demo.product.model.ProductDto;
import com.luna.jwt_demo.product.model.ProductEntity;

@Component
public class ProductMapper {

    public ProductDto toDto(ProductEntity entity) {
        return new ProductDto(
            entity.getId(), 
            entity.getName(), 
            entity.getQuantity()
        );
    }

    public ProductEntity toEntity(ProductDto dto) {
        return new ProductEntity(
            dto.name(),
            dto.quantity()
        );
    }

}
