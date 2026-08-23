package com.luna.jwt_demo.order.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDto(
    Long id,
    Long userId,
    List<Long> orderItemIdList,
    List<OrderItemResponse> orderItems,
    OrderStatus status
) {}

