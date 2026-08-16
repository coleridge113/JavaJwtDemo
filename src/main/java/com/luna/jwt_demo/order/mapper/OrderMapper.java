package com.luna.jwt_demo.order.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.model.OrderItemDto;
import com.luna.jwt_demo.order.model.OrderItemEntity;
import com.luna.jwt_demo.inventory.model.ProductDto;
import com.luna.jwt_demo.inventory.model.ProductEntity;

@Component
public class OrderMapper {

    public OrderEntity toEntity(OrderDto dto) {
        List<OrderItemEntity> orderItemEntities = dto.orderItems().stream()
            .map(this::toEntity)
            .collect(Collectors.toList());

        return new OrderEntity(
            dto.customerName(),
            orderItemEntities
        );
    }

    public OrderDto toDto(OrderEntity orderEntity) {
        List<OrderItemDto> orderItems = orderEntity.getOrderItems().stream()
            .map(this::toDto)
            .collect(Collectors.toList());

        return new OrderDto(
            orderEntity.getId(),
            orderEntity.getCustomerName(),
            orderItems
        );
    }

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

    public OrderItemDto toDto(OrderItemEntity entity) {
        return new OrderItemDto(
            entity.getId(),
            this.toDto(entity.getOrderEntity()),
            this.toDto(entity.getProductEntity()),
            entity.getQuantity()
        );
    }

    public OrderItemEntity toEntity(OrderItemDto dto) {
        return new OrderItemEntity(
            this.toEntity(dto.order()),
            this.toEntity(dto.product()),
            dto.quantity()
        );
    }
}
