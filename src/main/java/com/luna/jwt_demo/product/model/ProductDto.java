package com.luna.jwt_demo.product.model;

public record ProductDto(
    Long id,
    String name,
    Integer stockQuantity
) {}
