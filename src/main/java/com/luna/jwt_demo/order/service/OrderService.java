package com.luna.jwt_demo.order.service;

import org.springframework.stereotype.Service;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void createOrder(OrderDto orderDto) {
        OrderEntity entity = mapDtoToEntity(orderDto);
        repository.save(entity);
    }

    private OrderEntity mapDtoToEntity(OrderDto orderDto) {
        return new OrderEntity(
            orderDto.customerName(),
            orderDto.items()
        );
    }
}
