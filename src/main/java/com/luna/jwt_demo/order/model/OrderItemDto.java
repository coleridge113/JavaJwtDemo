package com.luna.jwt_demo.order.model;

import com.luna.jwt_demo.product.model.ProductDto;

public record OrderItemDto(
    Long id,
    OrderDto order,
    ProductDto product,
    Integer quantity
) {}
