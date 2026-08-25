package com.luna.jwt_demo.order.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.model.OrderItemDto;
import com.luna.jwt_demo.order.model.OrderItemEntity;
import com.luna.jwt_demo.order.model.OrderItemResponse;
import com.luna.jwt_demo.product.mapper.ProductMapper;
import com.luna.jwt_demo.product.model.ProductDto;

@Component
public class OrderMapper {

    private final ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public OrderDto toDto(OrderEntity orderEntity) {
        List<Long> orderIdList = new ArrayList<>();
        List<OrderItemResponse> orderItems = new ArrayList<>();

        orderEntity.getOrderItems().forEach(item -> {
            orderIdList.add(item.getId());
            orderItems.add(this.toResponse(item));
        });

        return new OrderDto(
            orderEntity.getId(),
            orderEntity.getUserId(),
            orderIdList,
            orderItems,
            orderEntity.getStatus()
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

    public OrderItemResponse toResponse(OrderItemEntity entity) {
        Integer quantity = entity.getQuantity();
        Long amountInCents = entity.getAmountInCents();
        Long totalAmountInCents = quantity * amountInCents;

        String productName = entity.getProductEntity() != null 
            ? entity.getProductEntity().getName() 
            : "Unknown Product";

        return new OrderItemResponse(
            entity.getId(),
            productName,
            quantity,
            amountInCents,
            totalAmountInCents
        );
    }

    public List<OrderItemResponse> toResponse(List<OrderItemEntity> entities) {
        return entities.stream()
            .map(entity -> {
                Integer quantity = entity.getQuantity();
                Long amountInCents = entity.getAmountInCents();
                Long totalAmountInCents = quantity * amountInCents;

                String productName = entity.getProductEntity() != null 
                    ? entity.getProductEntity().getName() 
                    : "Unknown Product";

                return new OrderItemResponse(
                    entity.getId(),
                    productName,
                    quantity,
                    amountInCents,
                    totalAmountInCents
                );
            })
            .toList();
    }
}
