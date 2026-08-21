package com.luna.jwt_demo.order.model;

import java.util.List;

public record OrderDto(
    Long id,
    Long userId,
    List<Long> orderItemIdList
) {}

