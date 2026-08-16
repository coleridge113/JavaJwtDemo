package com.luna.jwt_demo.order.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.model.OrderItemDto;
import com.luna.jwt_demo.order.model.OrderItemEntity;
import com.luna.jwt_demo.product.mapper.ProductMapper;
import com.luna.jwt_demo.product.model.ProductDto;
import com.luna.jwt_demo.product.model.ProductEntity;

@Component
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

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

    public OrderItemDto toDto(OrderItemEntity entity) {
        ProductDto productDto = productMapper.toDto(entity.getProductEntity());

        return new OrderItemDto(
            entity.getId(),
            this.toDto(entity.getOrderEntity()),
            productDto,
            entity.getQuantity()
        );
    }

    public OrderItemEntity toEntity(OrderItemDto dto) {
        ProductEntity productEntity = productMapper.toEntity(dto.product());

        return new OrderItemEntity(
            this.toEntity(dto.order()),
            productEntity,
            dto.quantity()
        );
    }
}
