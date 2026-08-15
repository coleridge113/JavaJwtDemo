package com.luna.jwt_demo.order.model;

public record OrderDto(
    Long id,
    String customerName,
    String items
) {}
