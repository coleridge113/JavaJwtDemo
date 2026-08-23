package com.luna.jwt_demo.order.model;

public record OrderUpdateResponse(
    Long orderId,
    OrderStatus status
) {}
