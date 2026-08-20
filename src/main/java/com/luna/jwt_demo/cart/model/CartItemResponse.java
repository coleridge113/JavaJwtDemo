package com.luna.jwt_demo.cart.model;

public record CartItemResponse(
    Long productId,
    String name,
    Long amountInCents,
    Integer quantity,
    Long totalInCents
) {}
