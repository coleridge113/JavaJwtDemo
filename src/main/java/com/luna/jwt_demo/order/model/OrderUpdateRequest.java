package com.luna.jwt_demo.order.model;

public record OrderUpdateRequest(
    Long orderId,
    OrderStatus status
) {}


