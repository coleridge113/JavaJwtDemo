package com.luna.jwt_demo.order.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
import com.luna.jwt_demo.order.mapper.OrderMapper;
import com.luna.jwt_demo.order.model.CreateOrderRequest;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.repository.OrderRepository;
import com.luna.jwt_demo.product.mapper.ProductMapper;
import com.luna.jwt_demo.product.model.ProductEntity;
import com.luna.jwt_demo.product.service.ProductService;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductService productService;
    private final RabbitTemplate rabbitTemplate;
    private final OrderMapper orderMapper;

    public OrderService(
        OrderRepository repository, 
        ProductService productService,
        RabbitTemplate rabbitTemplate,
        OrderMapper orderMapper,
        ProductMapper productMapper
    ) {
        this.repository = repository;
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
        this.orderMapper = orderMapper;
    }

    public void createOrder(CreateOrderRequest request) {
        OrderEntity order = new OrderEntity(request.customerName());
        request.items().forEach(item -> {
            ProductEntity product = productService.findProductEntityById(item.productId());
            order.addOrderItem(product, item.quantity());
        });

        repository.save(order);

        rabbitTemplate.convertAndSend(
            RabbitMqConfig.ORDERS_EXCHANGE,
            RabbitMqConfig.ORDER_CREATED_PATTERN,
            request.items()
        );
    }

    public OrderDto getOrderById(Long orderId) {
        OrderEntity order = repository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order with ID {} does not exist!", orderId));

        return orderMapper.toDto(order);
    }
}
