package com.luna.jwt_demo.order.model;

import java.util.List;

public record CreateOrderRequest(
    String customerName,
    List<OrderItemRequest> items
) {}
