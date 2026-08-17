package com.luna.jwt_demo.order.model;

import com.luna.jwt_demo.product.model.ProductDto;

public record OrderItemResponseDto(
    Long id,
    ProductDto product,
    Integer quantity
) {}
