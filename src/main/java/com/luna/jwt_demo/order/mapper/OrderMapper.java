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

@Component
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public OrderDto toDto(OrderEntity orderEntity) {
        List<Long> orderIdList = orderEntity.getOrderItems().stream()
            .map(order -> order.getId())
            .collect(Collectors.toList());

        return new OrderDto(
            orderEntity.getId(),
            orderEntity.getUserId(),
            orderIdList
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
}
