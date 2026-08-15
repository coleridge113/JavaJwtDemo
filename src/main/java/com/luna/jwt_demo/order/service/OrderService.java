package com.luna.jwt_demo.order.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createOrder(OrderDto orderDto) {
        OrderEntity entity = mapDtoToEntity(orderDto);
        OrderEntity savedEntity = repository.save(entity);
        OrderDto responseDto = mapEntityToDto(savedEntity);

        rabbitTemplate.convertAndSend(
            RabbitMqConfig.ORDERS_EXCHANGE,
            RabbitMqConfig.ORDER_CREATED_PATTERN,
            responseDto
        );
    }

    public OrderDto getOrderById(Long orderId) {
        OrderEntity order = repository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " does not exist!"));

        return mapEntityToDto(order);
    }

    private OrderEntity mapDtoToEntity(OrderDto dto) {
        return new OrderEntity(
            dto.customerName(),
            dto.items()
        );
    }

    private OrderDto mapEntityToDto(OrderEntity entity) {
        return new OrderDto(
            entity.getId(),
            entity.getCustomerName(),
            entity.getItems()
        );
    }
}
