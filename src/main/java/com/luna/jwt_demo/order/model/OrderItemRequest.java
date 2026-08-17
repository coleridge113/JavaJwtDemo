package com.luna.jwt_demo.order.model;

public record OrderItemRequest(
    Long productId,
    Integer quantity
) {}
