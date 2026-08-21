package com.luna.jwt_demo.cart.model;

public record CartItemRequest(
    Long productId,
    Integer quantity
) {}
