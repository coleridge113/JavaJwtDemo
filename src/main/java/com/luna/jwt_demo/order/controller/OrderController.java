package com.luna.jwt_demo.order.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.service.OrderService;
import com.luna.jwt_demo.order.model.OrderUpdateRequest;
import com.luna.jwt_demo.order.model.OrderUpdateResponse;
import com.luna.jwt_demo.order.model.OrderStatus;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final RabbitTemplate rabbitTemplate;

    public OrderController(OrderService orderService, RabbitTemplate rabbitTemplate) {
        this.orderService = orderService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/test")
    public ResponseEntity<OrderDto> createTestOrder(Authentication authentication) {

        OrderDto testOrder = new OrderDto(
            1111L,
            2222L,
            null,
            null,
            OrderStatus.PREPARING
        );

        rabbitTemplate.convertAndSend(
            RabbitMqConfig.ORDERS_EXCHANGE, 
            RabbitMqConfig.NOTIFICATION_PATTERN, 
            testOrder
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(testOrder);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();

        OrderDto order = orderService.createOrder(userId);

        rabbitTemplate.convertAndSend(
            RabbitMqConfig.ORDERS_EXCHANGE, 
            RabbitMqConfig.ORDER_CREATED_PATTERN, 
            order
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
        @PathVariable Long orderId,
        @RequestParam(name = "include", required = false) List<String> includes
    ) {
        OrderDto order = orderService.getOrderById(orderId, includes);
        return ResponseEntity.ok(order);
    }

    @PutMapping
    public ResponseEntity<OrderUpdateResponse> updateOrderStatus(
        @RequestBody OrderUpdateRequest request
    ) {
        OrderDto order = orderService.updateOrderStatus(request);
        OrderUpdateResponse response = new OrderUpdateResponse(
            order.id(),
            order.status()
        );
        return ResponseEntity.ok(response);
    }
}
