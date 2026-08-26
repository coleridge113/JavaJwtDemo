package com.luna.jwt_demo.product.mapper;

import org.springframework.stereotype.Component;

import com.luna.jwt_demo.product.event.ProductSyncEvent;
import com.luna.jwt_demo.product.model.ProductDocument;
import com.luna.jwt_demo.product.model.ProductDto;
import com.luna.jwt_demo.product.model.ProductEntity;

@Component
public class ProductMapper {

    public ProductDto toDto(ProductEntity entity) {
        return new ProductDto(
            entity.getId(), 
            entity.getName(), 
            entity.getStockQuantity(),
            entity.getAmountInCents()
        );
    }

    public ProductEntity toEntity(ProductDto dto) {
        return new ProductEntity(
            dto.name(),
            dto.stockQuantity(),
            dto.amountInCents()
        );
    }

    public ProductDto toDto(ProductDocument document) {
        return new ProductDto(
            Long.valueOf(document.getId()),
            document.getName(),
            document.getStockQuantity(),
            document.getAmountInCents()
        );
    }

    public ProductDocument toDocument(ProductSyncEvent event) {
        return new ProductDocument(
            event.id().toString(),
            event.name(),
            event.amountInCents(),
            event.stockQuantity()
        );
    }
}
