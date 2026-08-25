package com.luna.jwt_demo.product.event;

import java.util.List;
import com.luna.jwt_demo.product.model.ProductEntity;

public record ProductSyncEvent(
    Long id,
    String name,
    Long amountInCents,
    Integer stockQuantity,
    EventType type
) {

    public enum EventType {
        CREATED_OR_UPDATED,
        DELETED
    }

    public static ProductSyncEvent createdOrUpdated(ProductEntity entity) {
        return new ProductSyncEvent(
            entity.getId(),
            entity.getName(),
            entity.getAmountInCents(),
            entity.getStockQuantity(),
            EventType.CREATED_OR_UPDATED
        );
    }

    public static List<ProductSyncEvent> batchCreatedOrUpdated(List<ProductEntity> entities) {
        return entities.stream()
            .map(entity -> new ProductSyncEvent(
                    entity.getId(),
                    entity.getName(),
                    entity.getAmountInCents(),
                    entity.getStockQuantity(),
                    EventType.CREATED_OR_UPDATED
            ))
            .toList();
    }

    public static ProductSyncEvent deleted(Long id) {
        return new ProductSyncEvent(id, null, null, null, EventType.DELETED);
    }
}
