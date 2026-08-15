package com.luna.jwt_demo.order.service;

import org.springframework.stereotype.Service;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
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
