package com.luna.jwt_demo.order.model;

public record OrderItemResponse(
    Long orderItemId,
    String productName,
    Integer quantity,
    Long amountInCents,
    Long totalAmountInCents
) {}
