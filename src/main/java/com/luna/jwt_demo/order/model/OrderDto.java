package com.luna.jwt_demo.order.model;

import java.util.List;

public record OrderDto(
    Long id,
    String customerName,
    List<Long> orderItemIdList
) {}

