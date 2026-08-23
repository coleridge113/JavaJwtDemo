package com.luna.jwt_demo.order.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.service.OrderService;
import com.luna.jwt_demo.order.model.OrderUpdateRequest;
import com.luna.jwt_demo.order.model.OrderUpdateResponse;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();

        Long orderId = orderService.createOrder(userId);

        Map<String, Object> response = Map.of(
            "message", "Successfully created order!",
            "orderId", orderId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
