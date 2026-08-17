package com.luna.jwt_demo.order.model;

import java.util.List;

public record OrderResponseDto(
    Long id,
    String customerName,
    List<OrderItemResponseDto> items
) {}
