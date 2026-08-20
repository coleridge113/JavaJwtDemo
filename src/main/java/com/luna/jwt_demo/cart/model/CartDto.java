package com.luna.jwt_demo.cart.model;

import java.util.List;

public record CartDto(
    Long id,
    List<Long> productIds,
    Long totalAmountInCents
) {}
