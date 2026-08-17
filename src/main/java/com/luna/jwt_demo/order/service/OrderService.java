package com.luna.jwt_demo.order.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
import com.luna.jwt_demo.order.mapper.OrderMapper;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final OrderMapper orderMapper;

    public OrderService(
        OrderRepository repository, 
        RabbitTemplate rabbitTemplate,
        OrderMapper orderMapper
    ) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.orderMapper = orderMapper;
    }

    public void createOrder(OrderDto orderDto) {
        OrderEntity entity = orderMapper.toEntity(orderDto);
        OrderEntity savedEntity = repository.save(entity);
        OrderDto responseDto = orderMapper.toDto(savedEntity);

        rabbitTemplate.convertAndSend(
            RabbitMqConfig.ORDERS_EXCHANGE,
            RabbitMqConfig.ORDER_CREATED_PATTERN,
            responseDto
        );
    }

    public OrderDto getOrderById(Long orderId) {
        OrderEntity order = repository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order with ID {} does not exist!", orderId));

        return orderMapper.toDto(order);
    }
}
